var ssoUser = null;  //当前用户

// function loadUser() {
//   _root.loginUser({}, function (rtn, status) {
//     if (rtn.hasError) {
//       alert(rock.errorText(rtn, "获得当前用户失败！"));
//     } else {
//       ssoUser = rtn.result;
//     }
//     if (rock.isNull(ssoUser)) {
//       $("#loginUser").html("未知用户");
//     } else {
//       $("#loginUser").html(ssoUser.name);
//     }
//   })
// }

$(document).ready(function () {
  // loadUser();




  _root.loginUser({}, function (rtn) {
    if (rtn.hasError) {
      alert(rock.errorText(rtn, "获得当前用户失败！"));
    } else {
      ssoUser = rtn.result;
    }
    if (rock.isNull(ssoUser)||ssoUser.admin==0) {
      $("#loginUser").html("未知用户请登录！");
      document.getElementById("login-dropdown").style.display = "block";
      document.getElementById("profile-dropdown").style.display = "none";
    } else {
      $("#loginUser").html(ssoUser.name);
      document.getElementById("login-dropdown").style.display = "none";
      document.getElementById("profile-dropdown").style.display = "block";
      //登录后修改头像
      var mvc = rock.initSvr(["employee"]);
      var Service = mvc.findService("employee");
      if (ssoUser.hasPhoto) {
        $("#avatarImage").attr("src", Service.url("photo") + "?id=" + ssoUser.id);
      }
    }

  
  })








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
});