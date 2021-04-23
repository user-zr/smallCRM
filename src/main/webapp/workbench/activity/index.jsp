<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">

        $(function () {
            //日历框架
            $(".time").datetimepicker({
                minView: "month",
                language: "zh-CN",
                format: "yyyy-mm-dd",
                autoclose: true,
                todayBtn: true,
                pickerPosititon: "bottom-left"
            })

            //创建按钮绑定事件，打开添加操作的网态页面
            $("#addBtn").click(function () {

                // $("#crow")
                $.ajax({
                    url: "/activity/getUserList",
                    type: "get",
                    datatype: "json",
                    success: function (data) {
                        var html = ""

                        //遍历每一个传过来的n，也就是user对象
                        $.each(data, function (i, n) {
                            html += "<option value='" + n.id + "'>" + n.name + "</option>"
                        })
                        $("#create-owner").html(html)

                        //所有这下拉框处理完毕后的下拉框
                    }
                })

                //取得当前用户的id
                //在js中使用el表达式，el表达式必须要用在字符串下

                var id = "${user.id}"
                $("#create-owner").val(id)
                //点击后展示模态窗口
                $("#createActivityModal").modal("show")
            })

            //为保存按钮绑定事件
            $("#saveBtn").click(function () {


                $.ajax({
                    url: "/activity/saveActivity",
                    data: {
                        "owner": $.trim($("#create-owner").val()),
                        "name": $.trim($("#create-name").val()),
                        "startDate": $.trim($("#create-startDate").val()),
                        "endDate": $.trim($("#create-endDate").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-description").val())
                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data) {

                            //添加成功
                            // 局部刷新
                            //  pageList(1, 2)
                            pageList(1, $("#activityPage").bs_pagination("getOption", "rowsPerPage"))

                            //清空模态框里的内容
                            $("#activityAddForm")[0].reset()

                            //关闭模态窗口
                            $("#createActivityModal").modal("hide")
                        } else {
                            alert("创建失败")
                        }
                    }
                })
            })
            //页面加载完毕后出发方法
            pageList(1, 2)

            //为查询按钮绑定事件，触发pageList方法
            $("#searchBtn").click(function () {
                /*
                *点击查询按钮的时候，我们应该将搜索框中的信息保存卡来，保存到隐藏作用域中
                *
                * */
                $("#hidden-name").val($.trim($("#search-name").val()))
                $("#hidden-owner").val($.trim($("#search-owner").val()))
                $("#hidden-startDate").val($.trim($("#search-startDate").val()))
                $("#hidden-endDate").val($.trim($("#search-endDate").val()))

                pageList(1, 2)
            })

            //分页查询方法
            function pageList(pageNo, pageSize) {
                //将全选的复选框取消掉
                $("#qx").prop("checked", false)

                //查询前，将隐藏于中的信息取出来，重新赋予到搜索框中
                $("#search-name").val($.trim($("#hidden-name").val()))
                $("#search-owner").val($.trim($("#hidden-owner").val()))
                $("#search-startDate").val($.trim($("#hidden-startDate").val()))
                $("#search-endDate").val($.trim($("#hidden-endDate").val()))

                $.ajax({
                    url: "/activity/pageList",
                    type: "get",
                    data: {
                        "pageNo": pageNo,
                        "pageSize": pageSize,
                        //这个trim()直接防止数据库中数据出现空格的情况
                        "name": $.trim($("#search-name").val()),
                        "owner": $.trim($("#search-owner").val()),
                        "startDate": $.trim($("#search-startDate").val()),
                        "endDate": $.trim($("#search-endDate").val())
                    },
                    dataType: "json",
                    success: function (data) {

                        var html = "";

                        $.each(data.list, function (i, n) {

                            html += '<tr class="active">'
                            html += '<td><input type="checkbox" name="xz" value="' + n.id + '"/></td>'
                            html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'/activity/detail?id=' + n.id + '\';">' + n.name + '</a></td>';
                            html += '<td>' + n.owner + '</td>'
                            html += '<td>' + n.startDate + '</td>'
                            html += '<td>' + n.endDate + '</td>'
                            html += '</tr>'
                        })
                        $("#activityBody").html(html)

                        //计算总页数
                        //也就是卡片中的值
                        var totalPages = data.total % pageSize == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1;
                        //数据处理完毕后
                        $("#activityPage").bs_pagination({
                            currentPage: pageNo, // 页码
                            rowsPerPage: pageSize, // 每页显示的记录条数
                            maxRowsPerPage: 20, // 每页最多显示的记录条数
                            totalPages: totalPages, // 总页数
                            totalRows: data.total, // 总记录条数

                            visiblePageLinks: 3, // 显示几个卡片

                            showGoToPage: true,
                            showRowsPerPage: true,
                            showRowsInfo: true,
                            showRowsDefaultInfo: true,

                            //该回调函数在，点击分页函数的时候触发的
                            onChangePage: function (event, data) {
                                pageList(data.currentPage, data.rowsPerPage);
                            }
                        });

                    }
                })
            }

            //全选框绑定事件
            $("#qx").click(function () {
                $("input[name=xz]").prop("checked", this.checked)

            })

            // 这个方法是不行的，
            // $("input[name=xz]").click(function () {
            //      alert("123")
            // })

            //动态生成的元素不能以普通绑定的事件经行操作
            //也就是 js 生成元素是不可以用不同的绑定事件的
            /*
            * 动态生成的元素要以 on 方法来来触发事件
            * 语法：
            *    $(需要绑定元素的外层元素).on(绑定事件的方式，绑定jquery对象 ,回调函数)
            * */

            //选框全选后的全选框✔
            $("#activityBody").on("click", $("input[name=xz]"), function () {
                $("#qx").prop("checked", $("input[name=xz]").length == $("input[name=xz]:checked").length)
            })


            //删除按钮绑定事件
            $("#deleteBtn").click(function () {
                //找到复选框中所有对象
                var xzs = $("input[name=xz]:checked")

                if (xzs.length == 0) {
                    alert("请选择需要删除的记录")
                } else {
                    //拼接参数
                    var param = ""

                    for (var i = 0; i < xzs.length; i++) {
                        param += $(xzs[i]).val()
                        if (i != xzs.length - 1) {
                            param += ","
                        }
                    }

                    if (confirm("确定删除所选中的记录吗")) {

                        $.ajax({
                            url: "/activity/deleteActivity",
                            data: {"id": param},
                            type: "post",
                            dataType: "json",
                            success: function (data) {
                                if (data) {

                                    //删除成功后
                                    // pageList(1, 2)

                                    /*
                                    $("#activityPage").bs_pagination('getOption','currentPage')
                                         操作后停留在当前页

                                    $("#activityPage").bs_pagination("getOption","rowsPerPage"))
                                         操作后维持已经设置好的页面展现的记录数
                                    * */
                                    pageList(1, $("#activityPage").bs_pagination("getOption", "rowsPerPage"))

                                } else {
                                    alert("删除失败")
                                }
                            }
                        })
                    }
                }
            })

            //为修改按钮绑定事件
            $("#editBtn").click(function () {

                var xzs = $("input[name=xz]:checked")

                if (xzs.length == 0) {
                    alert("请选择修改的地方")
                } else if (xzs.length > 1) {
                    alert("请选择一处地方进行修改")
                } else {
                    var aid = xzs.val()

                    $.ajax({
                        url: "/activity/getUserListAndActivity",
                        data: {"aid": aid},
                        type: "get",
                        dataType: "json",
                        success: function (data) {
                            var html = ""

                            //处理下拉框
                            $.each(data.ulist, function (i, n) {
                                html += "<option value=' " + n.id + " '>" + n.name + "</option>"
                            })
                            $("#edit-owner").html(html)

                            //处理单条activity
                            $("#edit-id").val(data.a.id);
                            $("#edit-name").val(data.a.name);
                            $("#edit-startDate").val(data.a.startDate);
                            $("#edit-endDate").val(data.a.endDate);
                            $("#edit-cost").val(data.a.cost);
                            $("#edit-description").val(data.a.description);

                            //所有值填好后，打开模态窗口
                            $("#editActivityModal").modal("show")
                        }
                    })
                }
            })

            //为更新按钮绑定操作
            $("#updateBtn").click(function () {
                $.ajax({
                    url: "/activity/updateActivity",
                    data: {
                        "id": $.trim($("#edit-id").val()),
                        "owner": $.trim($("#edit-owner").val()),
                        "name": $.trim($("#edit-name").val()),
                        "startDate": $.trim($("#edit-startDate").val()),
                        "endDate": $.trim($("#edit-endDate").val()),
                        "cost": $.trim($("#edit-cost").val()),
                        "description": $.trim($("#edit-description").val())
                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data) {
                            //修改成功后
                            //刷新列表
                            // pageList(1, 2)
                            pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                                , $("#activityPage").bs_pagination("getOption", "rowsPerPage"))

                            //关闭修改操作的模态窗口
                            $("#editActivityModal").modal("hide")
                        } else {
                            alert("修改失败")
                        }
                    }

                })
            })


        });

    </script>
</head>
<body>

<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form id="activityAddForm" class="form-horizontal" role="form">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-owner">

                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startData" readonly>
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endData" readonly>
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">

                    <input type="hidden" id="edit-id">

                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-owner">

                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-startDate">
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-endDate">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <%--
                                    关于文本域
                                        1.一定要以标签对的形式来呈现，正常状态下标签对要紧紧挨着
                                        2.textarea虽然是以标签对的形式来呈现的，但是他是属于表单元的范畴的
                                            所有的textarea的取值和赋值操作，应该统一使用 val（）方法（而不是html（）方法）
                                --%>
                            <textarea class="form-control" rows="3" id="edit-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="search-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="search-endDate">
                    </div>
                </div>

                <button type="button" id="searchBtn" class="btn btn-default">查询</button>

            </form>
        </div>

        <%--data-toggle="modal" data-target="#createActivityModal"--%>
        <%--data-toggle="modal" data-target="#editActivityModal"--%>

        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="addBtn">
                    <span class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button type="button" class="btn btn-default" id="editBtn"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="deleteBtn"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input id="qx" type="checkbox"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="activityBody">
                <%--<tr class="active">--%>
                <%--<td><input type="checkbox"/></td>--%>
                <%--<td><a style="text-decoration: none; cursor: pointer;"--%>
                <%--onclick="window.location.href='detail.jsp';">发传单</a></td>--%>
                <%--<td>zhangsan</td>--%>
                <%--<td>2020-10-10</td>--%>
                <%--<td>2020-10-20</td>--%>
                <%----%>
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage"></div>

        </div>

    </div>

</div>
</body>
</html>