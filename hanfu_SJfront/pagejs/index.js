$(document).ready(function () {

    // var {
    //   OverlayScrollbars,
    //   ScrollbarsHidingPlugin,
    //   SizeObserverPlugin,
    //   ClickScrollPlugin
    // } = OverlayScrollbarsGlobal;
    // const osInstance = OverlayScrollbars(document.querySelector('body'), {});


    //头部搜索框处理(不需要可以删除,不明白的可以看bootstrap-admin官方文档)
    $(document).on('search.bsa.navbar-search', function (e, inputValue, data) {
      //先得到请求地址,组合后大概是这样pages/search.html?keyword=dsadsa&type=article&user=admin2
      let url = data.action + '?keyword=' + inputValue + '&' + $.param(data.params);

      //然后通过tab打开一个搜索结果的窗口
      Quicktab.get('.qtab').addTab({
        title: '<i class="bi bi-search"></i><span class="text-danger ms-2">' + inputValue + '</span>',
        url: url,
        close: true,
      })
    })


    $(function ($) {

_root.loginUser({}, function (rtn) {
    var ssoUser=null;
    if (rtn.hasError) {
      alert(rock.errorText(rtn, "获得当前用户失败！"));
    } else {
      ssoUser = rtn.result;
    }
    if (rock.isNull(ssoUser)) {
      $("#loginUser").html("未知用户请登录！");
      document.getElementById("login-dropdown").style.display = "block";
      document.getElementById("logout-dropdown").style.display = "none";
    } else {
      $("#loginUser").html(ssoUser.name);
      document.getElementById("login-dropdown").style.display = "none";
      document.getElementById("logout-dropdown").style.display = "block";

        //登录后显示头像
        var mvc = rock.initSvr(["merchant"]);
        var Service = mvc.findService("merchant");
        if (ssoUser.hasPhoto) {

          $("#userImage").attr("src", Service.url("photo") + "?id=" + ssoUser.id);
        }
    }
  })
});

 //退出登录
 $(document).on('click', '.bsa-logout', function (e) {
    e.preventDefault();
    $.modal({
      body: '确定要退出吗？',
      cancelBtn: true,
      ok: function () {

        _root.logout({}, (rtn) => {
          if (rtn.hasError || !rtn.result) {
            $.toasts({
              type: 'danger',
              content: '注销登录出错!',

            })

          } else {

            $.toasts({
              type: 'success',
              content: '退出登录成功！',
              onHidden: function () {
                top.location.replace('pages/login.html');
              }
            })

          }
        });



      }
    })


  });


//注销账号
$(document).on('click', '.bsa-del', function (e) {
  e.preventDefault();
  $.modal({
    body: '确定要注销账号吗？（此操作将删除此账号的所有信息！请谨慎考虑！）',
    cancelBtn: true,

    ok: function () {

        window.modalInstance = $.modal({

            url: 'pages/user_del.html',
            title: '注销账号',
            //禁用掉底部的按钮区域
            buttons: [],
            modalDialogClass: 'modal-dialog-centered modal-lg',

          });

    }
  })


});


  })