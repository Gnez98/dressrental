//租赁窗口中时间标签的内容改变时执行
function cg() {
    $("#savemsg").attr("disabled", false);
    var rt = $("#time").val().split("-");
    var ny = new Date().getFullYear();
    var nm = new Date().getMonth() + 1;
    var nd = new Date().getDate();
    if (rt[0] < ny) {
        alert("日期不能早于今天")
        $("#savemsg").attr("disabled", true);
    } else if (rt[0] == ny) {
        if (rt[1] < nm) {
            alert("日期不能早于今天")
            $("#savemsg").attr("disabled", true);
        } else if (rt[1] == nm) {
            if (rt[2] < nd) {
                alert("日期不能早于今天")
                $("#savemsg").attr("disabled", true);
            } else {
                $("#savemsg").attr("disabled", false);
            }
        }
    }
}
//点击租赁礼服时执行
function borrow() {
    var url =getProjectPath()+ "/dress/borrowDress";
    $.post(url, $("#borrowDress").serialize(), function (response) {
        alert(response.message)
        if (response.success == true) {
            window.location.href = getProjectPath()+"/dress/search";
        }
    })
}

//重置添加和编辑窗口中输入框的内容
function resetFrom() {
    $("#aoe").attr("disabled",true)
    var $vals=$("#addOrEditDress input");
    $vals.each(function(){
        $(this).attr("style","").val("")
    });
}
//重置添加和编辑窗口中输入框的样式
function resetStyle() {
    $("#aoe").attr("disabled",false)
    var $vals=$("#addOrEditDress input");
    $vals.each(function(){
        $(this).attr("style","")
    });
}
//查询id对应的礼服信息，并将礼服信息回显到编辑或租赁的窗口中
function findDressById(id,doname) {
    resetStyle()
    var url = getProjectPath()+"/dress/findById?id=" + id;
    $.get(url, function (response) {
        //如果是编辑礼服，将获取的礼服信息回显到编辑的窗口中
        if(doname=='edit'){
            $("#ebid").val(response.data.id);
            $("#ebname").val(response.data.name);
            $("#ebisbn").val(response.data.isbn);
            $("#ebpress").val(response.data.press);
            $("#ebauthor").val(response.data.author);
            $("#ebpagination").val(response.data.pagination);
            $("#ebprice").val(response.data.price);
            $("#ebstatus").val(response.data.status);
        }
        //如果是租赁礼服，将获取的礼服信息回显到租赁的窗口中
        if(doname=='borrow'){
            $("#savemsg").attr("disabled",true)
            $("#time").val("");
            $("#bid").val(response.data.id);
            $("#bname").val(response.data.name);
            $("#bisbn").val(response.data.isbn);
            $("#bpress").val(response.data.press);
            $("#bauthor").val(response.data.author);
            $("#bpagination").val(response.data.pagination);
        }
    })
}
//点击添加或编辑的窗口的确定按钮时，提交礼服信息
function addOrEdit() {
    //获取表单中礼服id的内容
    var ebid = $("#ebid").val();
    //如果表单中有礼服id的内容，说明本次为编辑操作
    if (ebid > 0) {
        var url = getProjectPath()+"/dress/editDress";
        $.post(url, $("#addOrEditDress").serialize(), function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath()+"/dress/search";
            }
        })
    }
    //如果表单中没有礼服id，说明本次为添加操作
    else {
        var url = getProjectPath()+"/dress/addDress";
        $.post(url, $("#addOrEditDress").serialize(), function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath()+"/dress/search";
            }
        })
    }
}
//归还礼服时执行
function returnDress(bid) {
    var r = confirm("确定归还礼服?");
    if (r) {
        var url = getProjectPath()+"/dress/returnDress?id=" + bid;
        $.get(url, function (response) {
            alert(response.message)
            //归还成功时，刷新当前租赁的列表数据
            if (response.success == true) {
                window.location.href = getProjectPath()+"/dress/searchBorrowed";
            }
        })
    }
}
//确认礼服已经归还
function returnConfirm(bid) {
    var r = confirm("确定礼服已归还?");
    if (r) {
        var url = getProjectPath()+"/dress/returnConfirm?id=" + bid;
        $.get(url, function (response) {
            alert(response.message)
            //归还确认成功时，刷新当前租赁的列表数据
            if (response.success == true) {
                window.location.href = getProjectPath()+"/dress/searchBorrowed";
            }
        })
    }
}
//检查礼服信息的窗口中，礼服信息填写是否完整
function checkval(){
    var $inputs=$("#addOrEditTab input")
    var count=0;
    $inputs.each(function () {
        if($(this).val()==''||$(this).val()=="不能为空！"){
            count+=1;
        }
    })
    //如果全部输入框都填写完整，解除确认按钮的禁用状态
    if(count==0){
        $("#aoe").attr("disabled",false)
    }
}
//页面加载完成后，给礼服模态窗口的输入框绑定失去焦点和获取焦点事件
$(function() {
    var $inputs=$("#addOrEditDress input")
    var eisbn="";
    $inputs.each(function () {
        //给输入框绑定失去焦点事件
        $(this).blur(function () {
            if($(this).val()==''){
                $("#aoe").attr("disabled",true)
                $(this).attr("style","color:red").val("不能为空！")
            }
            else if($(this).attr("name")=="isbn"&&eisbn!==$(this).val()){
                if($(this).val().length!=13){
                    $("#aoe").attr("disabled",true)
                    alert("必须为13位数的礼服编号，请重新输入！")
                    $(this).val("")
                }
            }else{
                checkval()
            }
        }).focus(function () {
            if($(this).val()=='不能为空！'){
                $(this).attr("style","").val("")
            }else{
                $(this).attr("style","")
            }
            if($(this).attr("name")=="isbn"){
                eisbn=$(this).val();
            }
        })
    })
});


//重置添加和编辑窗口中输入框的内容
function resetConsumerFrom() {
    $("#savemsg").attr("disabled",true)
    $("#addmsg").html("")
    var $vals=$("#addConsumer input");
    $vals.each(function(){
        $(this).attr("style","").val("")
    });

}
function findConsumerById(uid) {
    var url = getProjectPath()+"/consumer/findById?id=" + uid;
    $.get(url, function (response) {
        $("#uid").val(response.id);
        $("#uname").val(response.name);
        $("#pw").val(response.password);
        $("#urole").val(response.role);
        $("#uemail").val(response.email);
        $("#uhire").val(response.hiredate);

    })
}

function editConsumer() {
    var url =getProjectPath()+ "/consumer/editConsumer";
    $.post(url, $("#editConsumer").serialize(), function (response) {
        alert(response.message)
        if (response.success == true) {
            window.location.href = getProjectPath()+"/consumer/search";
        }
    })
}


function changeVal() {
    $("#addmsg").html("")
}

function checkVal() {
    $("#savemsg").attr("disabled", false);
    $("#addmsg").html("")
    var adduname = $("#adduname").val();
    var adduemail = $("#adduemail").val();
    var addPw = $("#addPw").val();
    var addtime = $("#time").val();
    if ($.trim(adduname) == '') {
        $("#savemsg").attr("disabled", true);
        $("#addmsg").html("姓名不能为空")
    } else {
        checkName(adduname);
        if ($.trim(adduemail) == '') {
            $("#savemsg").attr("disabled", true);
            $("#addmsg").html("邮箱不能为空")
        } else if ($.trim(adduemail) != '') {
            checkEmail(adduemail);
                if ($.trim(addPw) == '') {
                $("#savemsg").attr("disabled", true);
                $("#addmsg").html("密码不能为空")
            }else if($.trim(addPw) != ''){
                if($.trim(addtime) == ''){
                    $("#savemsg").attr("disabled", true);
                    $("#addmsg").html("入职日期不能为空")
                }else{
                    cg()
                }
            }
        }
    }
}

function checkName(name) {
    var url = getProjectPath()+"/consumer/checkName?name=" + name;
    $.post(url, function (response) {
        if (response.success != true) {
            $("#savemsg").attr("disabled", true);
            $("#addmsg").html(response.message)
        }
    })
}

function checkEmail(email) {
    var url = getProjectPath()+"/consumer/checkEmail?email=" + email;
    $.post(url, function (response) {
        if (response.success != true) {
            $("#savemsg").attr("disabled", true);
            $("#addmsg").html(response.message)
        }
    })
}

function saveConsumer() {
    var url =getProjectPath()+"/consumer/addConsumer";
    $.post(url, $("#addConsumer").serialize(), function (response) {
        alert(response.message)
        if (response.success == true) {
            window.location.href =  getProjectPath()+"/consumer/search";
        }
    })
}
function delConsumer(uid) {
    var r = confirm("确定办理工号：" + uid + "的离职?");
    if (r) {
        var url = getProjectPath() + "/consumer/delConsumer?id=" + uid;
        $.get(url, function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath() + "/consumer/search";
            }
        })
    }
}
//删除员工信息时执行
function delConsumer1(uid) {
    var r = confirm(`确定删除${uid}员工信息`);
    if (r) {
        var url = getProjectPath()+"/consumer/delConsumer1?id=" + uid;
        $.get(url, function (response) {
            alert(response.message)
            //归还成功时，刷新当前员工的列表数据
            if (response.success == true) {
                window.location.href = getProjectPath()+"/consumer/search";
            }
        })
    }
}
//获取当前项目的名称
    function getProjectPath() {
        //获取主机地址之后的目录，如： cloudlibrary/admin/dresss.jsp
        var pathName = window.document.location.pathname;
        //获取带"/"的项目名，如：/cloudlibrary
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return projectName;
    }

    /**
     * 数据展示页面分页插件的参数
     * cur 当前页
     * total 总页数
     * len   显示多少页数
     * pagesize 1页显示多少条数据
     * gourl 页码变化时 跳转的路径
     * targetId 分页插件作用的id
     */
    var pageargs = {
        cur: 1,
        total: 0,
        len: 5,
        pagesize: 5,
        gourl: "",
        targetId: 'pagination',
        callback: function (total) {
            var oPages = document.getElementsByClassName('page-index');
            for (var i = 0; i < oPages.length; i++) {
                oPages[i].onclick = function () {
                    changePage(this.getAttribute('data-index'), pageargs.pagesize);
                }
            }
            var goPage = document.getElementById('go-search');
            goPage.onclick = function () {
                var index = document.getElementById('yeshu').value;
                if (!index || (+index > total) || (+index < 1)) {
                    return;
                }
                changePage(index, pageargs.pagesize);
            }
        }
    }
    /**
     *礼服查询栏的查询参数
     * name 礼服名称
     * author 礼服作者
     * press 礼服出版社
     */
    var dressVO = {
        name: '',
        author: '',
        press: ''
    }
    /**
     *租赁记录查询栏的查询参数
     * name 礼服名称
     * borrower 租赁人
     */
    var leaseRVO = {
        dressname: '',
        borrower: ''
    }
    var consumerVO = {
        name: '',
        id: ''
    }

//数据展示页面分页插件的页码发送变化时执行
    function changePage(pageNo, pageSize) {
        pageargs.cur = pageNo;
        pageargs.pagesize = pageSize;
        document.write("<form action=" + pageargs.gourl + " method=post name=form1 style='display:none'>");
        document.write("<input type=hidden name='pageNum' value=" + pageargs.cur + " >");
        document.write("<input type=hidden name='pageSize' value=" + pageargs.pagesize + " >");
        //如果跳转的是礼服信息查询的相关链接，提交礼服查询栏中的参数
        if (pageargs.gourl.indexOf("dress") >= 0) {
            document.write("<input type=hidden name='name' value=" + dressVO.name + " >");
            document.write("<input type=hidden name='author' value=" + dressVO.author + " >");
            document.write("<input type=hidden name='press' value=" + dressVO.press + " >");
        }
        //如果跳转的是员工信息查询的相关链接，提交员工信息查询栏中的参数
        if (pageargs.gourl.indexOf("consumer") >= 0) {
            document.write("<input type=hidden name='id' value=" + consumerVO.id + " >");
            document.write("<input type=hidden name='name' value=" + consumerVO.name + " >");
        }
        //如果跳转的是礼服记录查询的相关链接，提交礼服记录查询栏中的参数
        if (pageargs.gourl.indexOf("leaseR") >= 0) {
            document.write("<input type=hidden name='dressname' value=" + leaseRVO.dressname + " >");
            document.write("<input type=hidden name='borrower' value=" + leaseRVO.borrower + " >");
        }
        document.write("</form>");
        document.form1.submit();
        pagination(pageargs);
    }


