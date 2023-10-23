loadTableData();
function loadTableData() {

  _root.loginUser(null, function (rtn, status) {
    if (rtn.hasError) {
      alert(rock.errorText(rtn, "连接到服务器失败！"));
    } else if (rtn.notNull) {

      $(document).ready(function () {



        //默认0代表全部
        let msgType = 0;

        $('#msgType').on('click', 'button', function () {
          msgType = $(this).attr('data-type');

          //移除所有的active类
          $('#msgType button').removeClass('active');
          //给当前button添加激活类
          $(this).addClass('active');

          $('#table').bootstrapTable('refresh');
          // $('#table').bootstrapTable('selectPage', 1)//跳转到第一页
        })

        // 销毁第一个表格实例
        $('#table').bootstrapTable('destroy');
        $('#table').bootstrapTable({
          //配置语言
          locale: 'zh-CN',
          //设置高度，就可以固定表头
          // height: 380,
          //请求地址
          url: 'http://127.0.0.1:8080/hanfu/unlock/queryPage',
          queryParamsType: "page",
          responseHandler: function (res) {
            var data = {};
            if (res.hasError) {
              alert(rock.errorText(res, "未登录！无法获得数据列表!"));
            }
            else {
              var ps = res.result;
              data.total = ps.totalCount;
              data.rows = ps.content;
            }
            // alert(res.data);
            return data;
            //return res.data;
          },
          //是否开启分页
          pagination: true,
          //是客户端分页还是服务端分页  'client','server',由于演示没有后端提供服务，所以采用前端分页演示
          sidePagination: 'server',
          // 初始化加载第一页，默认第一页
          pageNumber: 1,
          //默认显示几条
          pageSize: 5,
          //可供选择的每页的行数 - (亲测大于1000存在渲染问题)
          pageList: [5, 10, 25, 50, 100],
          //在上百页的情况下体验较好 - 能够显示首尾页
          paginationLoop: true,
          // 展示首尾页的最小页数
          paginationPagesBySide: 2,
          // 唯一ID字段
          uniqueId: 'id',
          // 每行的唯一标识字段
          idField: 'id',
          // 是否启用点击选中行
          clickToSelect: false,
          // 请求得到的数据类型
          dataType: 'json',
          // 请求方法
          method: 'get',
          // 工具按钮容器
          //toolbar: '#toolbar',
          // 总数字段
          totalField: 'total',
          // 当字段为 undefined 显示
          undefinedText: '-',
          // 排序方式
          sortOrder: "desc",
          //默认排序
          sortName: "requestTime",
          // 按钮的类
          buttonsClass: 'light',
          // 类名前缀
          buttonsPrefix: 'btn',
          // 图标前缀
          iconsPrefix: 'bi',
          // 图标大小 undefined sm lg
          iconSize: undefined,
          // 图标的设置  这里只做了一个演示，可设置项目参考 https://examples.bootstrap-table.com/#options/table-icons.html
          icons: {
            fullscreen: 'bi-arrows-fullscreen',
          },
          //加载模板,不改的话，默认的会超出不好看
          loadingTemplate: function () {
            return '<div class="spinner-grow" role="status"><span class="visually-hidden">Loading...</span></div>';
          },
          //params是一个对象
          queryParams: function (options) {

            var param = rock.formData($("#searchForm"));
            //var param = {};
            param.pageSize = options.pageSize;
            param.pageNum = options.pageNumber;
            param.sort = options.sortName + " " + options.sortOrder;
            param.limit = options.limit;
            param.sortOrder = options.sortOrder;
            return param;

            // return {
            //   // 每页数据量
            //   limit: params.limit,
            //   // sql语句起始索引
            //   offset: params.offset,
            //   page: (params.offset / params.limit) + 1,
            //   // 排序的列名
            //   sort: params.sort,
            //   // 排序方式 'asc' 'desc'
            //   sortOrder: params.order,
            //   // 消息类型
            //   msgType: 1,
            // }
          },
          //列
          columns: [
            {
              checkbox: true,
              //是否显示该列
              visible: true,
            },
            {
              title: '用户账号',
              field: 'code',
              align: 'left',
              // formatter: formatContent
            },
            {
              title: '姓名/商品名称',
              field: 'name',
              align: 'center',
            },
            {
              title: '电话号',
              field: 'phone',
              align: 'center',
            },
            {
              title: '申请提交时间',
              field: 'requestTime',
              align: 'center',
              sortable: true,
              formatter: function (value) {
                // 使用 Date 对象将 value 转换为日期时间
                var datetime = new Date(value);

                // 获取年、月、日、小时、分钟、秒
                var year = datetime.getFullYear();
                var month = String(datetime.getMonth() + 1).padStart(2, '0');
                var day = String(datetime.getDate()).padStart(2, '0');
                var hour = String(datetime.getHours()).padStart(2, '0');
                var minute = String(datetime.getMinutes()).padStart(2, '0');
                var second = String(datetime.getSeconds()).padStart(2, '0');

                // 返回格式化后的日期时间字符串
                return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
              }
            },
            {
              title: '申请处理时间',
              field: 'processingTime',
              align: 'center',
              sortable: true,
              formatter: function (value) {
                // 使用 Date 对象将 value 转换为日期时间
                var datetime = new Date(value);

                // 获取年、月、日、小时、分钟、秒
                var year = datetime.getFullYear();
                var month = String(datetime.getMonth() + 1).padStart(2, '0');
                var day = String(datetime.getDate()).padStart(2, '0');
                var hour = String(datetime.getHours()).padStart(2, '0');
                var minute = String(datetime.getMinutes()).padStart(2, '0');
                var second = String(datetime.getSeconds()).padStart(2, '0');

                // 返回格式化后的日期时间字符串
                return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
              }
            },
            {
              title: '申诉理由',
              field: 'reason',
              align: 'center',
              formatter: formatContent
            },
            {
              title: '申诉类型',
              field: 'type',
              align: 'center',
              sortable: true,
              formatter: function (value) {
                switch (value) {
                  case 1:
                    return "商城员工";
                  case 2:
                    return "顾客";
                  case 3:
                    return "商家";
                  default:
                    return "违规商品";
                }

              },
            },
            {
              title: '申请处理状态',
              field: 'status',
              align: 'center',
              sortable: true,
              formatter: function (value) {
                return value == 1 ? "已处理" : "未处理";
              },
            },

            {
              title: '操作',
              align: 'center',
              // formatter: formatAction,
              events: {
                'click .edit-btn': function (event, value, row, index) {
                  event.stopPropagation();

                  window.modalInstance = $.modal({
                    onShow: function () {
                      // 将所选行的数据存储到 sessionStorage
                      sessionStorage.setItem('selectedUserData', JSON.stringify(row));
                    },
                    url: 'user-edit.html',
                    title: '编辑用户信息',
                    //禁用掉底部的按钮区域
                    buttons: [],
                    modalDialogClass: 'modal-dialog-centered modal-lg',
                    onHidden: function (obj, data) {
                      if (data === true) {
                        //刷新当前数据表格
                        $('#table').bootstrapTable('refresh');
                        $('#table').bootstrapTable('selectPage', 1)//跳转到第一页
                      }
                      // 使用完数据后清除 sessionStorage 中的数据
                      sessionStorage.removeItem('selectedUserData');
                    }
                  });

                },
                'click .del-btn': function (event, value, row, index) {
                  event.stopPropagation();
                  window.modalInstance = $.modal({

                    onShow: function () {
                      // 将所选行的数据存储到 sessionStorage
                      sessionStorage.setItem('selectedUserData', JSON.stringify(row));
                    },
                    url: 'ban.html',
                    title: '封禁用户',
                    //禁用掉底部的按钮区域
                    buttons: [],
                    modalDialogClass: 'modal-dialog-centered modal-lg',
                    onHidden: function (obj, data) {
                      if (data === true) {
                        //刷新当前数据表格
                        $('#table').bootstrapTable('refresh');
                        $('#table').bootstrapTable('selectPage', 1)//跳转到第一页
                      }
                      // 使用完数据后清除 sessionStorage 中的数据
                      sessionStorage.removeItem('selectedUserData');
                    }

                  })

                },

                'click .unlock-btn': function (event, value, row, index) {
                  event.stopPropagation();

                  $.ajax({
                    //跨域
                    xhrFields: {
                      withCredentials: true
                    },
                    //url: 'http://127.0.0.1:8080/hanfu/ban/queryByName?propName=userId&propValue=' + encodeURIComponent(row.id),
                    url: 'http://127.0.0.1:8080/hanfu/ban/queryByBanId',
                    method: 'get',
                    data: {
                      userId: row.id,
                      type: 1
                    },
                  }).then(response => {

                    //用数组在取出时单个的reason不会拆开
                    var reasons = [];
                    response.result.forEach(ban => {
                      //reasons += ban.reason;
                      reasons.push(ban.reason);
                    });


                    // var times = '';
                    // response.result.forEach(ban => {
                    //   times += ban.beginTime ;
                    // });
                    // console.log(times)

                    // 遍历所有 Ban 对象，将 beginTime 格式化为日期时间字符串
                    var formattedBeginTimes = response.result.map(ban => {
                      // 将时间戳转换为 Date 对象
                      var beginTime = new Date(ban.beginTime);
                      // 使用 Intl.DateTimeFormat 对象进行格式化
                      var formattedBeginTime = new Intl.DateTimeFormat('zh-CN', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit',
                        hour: '2-digit',
                        minute: '2-digit',
                        second: '2-digit'
                      }).format(beginTime);
                      return formattedBeginTime;
                    });

                    // 将格式化后的 beginTime 数组和 reasons 字符串连接起来
                    var bodyContent = '';
                    for (var i = 0; i < formattedBeginTimes.length; i++) {
                      bodyContent += '封禁日期：' + formattedBeginTimes[i] + '<br>';
                      bodyContent += '封禁理由：' + reasons[i] + '<br><br>';
                    }
                    // // 将时间戳转换为 Date 对象
                    // var beginTime = new Date(times);
                    // // 使用日期时间格式函数格式化日期时间
                    // var formattedBeginTime = beginTime.getFullYear() + '-' + (beginTime.getMonth() + 1) + '-' + beginTime.getDate() + ' ' +
                    //   beginTime.getHours() + ':' + beginTime.getMinutes() + ':' + beginTime.getSeconds();

                    window.modalInstance = $.modal({

                      body: '确定要解封此用户:\n' + row.name + '?' + '<br><br>' + '此用户已被封禁\n' + response.result.length + '\n次' + '<br><br>' + bodyContent,

                      cancelBtn: true,

                      ok: function () {
                        $.ajax({
                          //跨域
                          xhrFields: {
                            withCredentials: true
                          },
                          url: 'http://127.0.0.1:8080/hanfu/employee/updateEmployee',
                          method: 'post',
                          data: { status: 1, id: row.id },
                        }).then(response => {
                          if (response.result) {
                            $.toasts({
                              type: 'success',
                              content: '解封成功！',
                              delay: 1500,
                              onHidden: function () {
                                //刷新当前数据表格
                                $('#table').bootstrapTable('refresh');
                                $('#table').bootstrapTable('selectPage', 1)//跳转到第一页
                              }
                            })
                          }
                        });

                      }, cancel: function () {
                        //刷新当前数据表格
                        $('#table').bootstrapTable('refresh');
                        $('#table').bootstrapTable('selectPage', 1)//跳转到第一页

                      }


                    })


                  });



                },

                'click .role-btn': function (event, value, row, index) {
                  event.stopPropagation();

                  window.rolemodal = $.modal({
                    onShow: function () {
                      // 将所选行的数据存储到 sessionStorage
                      sessionStorage.setItem('selectedUserData', JSON.stringify(row));
                    },
                    url: 'user-resetPwd.html',
                    title: '重置用户密码',
                    //禁用掉底部的按钮区域
                    buttons: [],
                    modalDialogClass: 'modal-dialog-centered modal-lg',
                    onHidden: function (obj, data) {
                      if (data === true) {
                        //刷新当前数据表格
                        $('#table').bootstrapTable('refresh');
                        $('#table').bootstrapTable('selectPage', 1)//跳转到第一页
                      }
                      // 使用完数据后清除 sessionStorage 中的数据
                      sessionStorage.removeItem('selectedUserData');
                    }
                  })

                }
              }
            }


          ]
        });




        function formatContent(val, rows) {
          return `<a class="text-decoration-none text-body" href="message_detail.html?id=${rows.id}">${val}</a>`;
        }

      });


      //重置处理
      $('.bsa-reset-btn').on('click', function () {

        //把所有的字段都恢复默认值
        $('#name').val('');
        $('#phone').val('');
        $('#joinTime').val('');
        $('#endTime').val('');
        $('#status').selectpicker('val', ['']).trigger("change");
        $('#admin').selectpicker('val', ['']).trigger("change");
        //刷新回到第一页
        $('#table').bootstrapTable('refresh');
        $('#table').bootstrapTable('selectPage', 1)//跳转到第一页
      })

      //==============================日期时间插件====================================


      //日期时间的翻译，由于该插件没有内置中文翻译，需要手动通过选项进行翻译
      let td_zh = {
        today: '回到今天',
        clear: '清除选择',
        close: '关闭选取器',
        selectMonth: '选择月份',
        previousMonth: '上个月',
        nextMonth: '下个月',
        selectYear: '选择年份',
        previousYear: '上一年',
        nextYear: '下一年',
        selectDecade: '选择十年',
        previousDecade: '上一个十年',
        nextDecade: '下一个十年',
        previousCentury: '上一个世纪',
        nextCentury: '下一个世纪',
        pickHour: '选取时间',
        incrementHour: '增量小时',
        decrementHour: '递减小时',
        pickMinute: '选取分钟',
        incrementMinute: '增量分钟',
        decrementMinute: '递减分钟',
        pickSecond: '选取秒',
        incrementSecond: '增量秒',
        decrementSecond: '递减秒',
        toggleMeridiem: '切换上下午',
        selectTime: '选择时间',
        selectDate: '选择日期',
      }

      //自定义日期格式插件
      let td6 = new tempusDominus.TempusDominus(document.getElementById('joinTime'), {
        //本地化控制
        localization: {
          ...td_zh,//展开语法
          format: 'yyyy-MM-dd HH:mm:ss',
        },
        //布局控制
        display: {
          //视图模式(选择年份视图最先开始)
          viewMode: 'calendar',
          components: {
            //是否开启日历，false:则是时刻模式
            calendar: true,
            //支持年份选择
            year: true,
            //是否开启月份选择
            month: true,
            //支持日期选择
            date: true,
            //底部按钮中那个时刻选择是否启用，false则表示隐藏，下面三个需要该选项为true时有效
            clock: true,
            //时
            hours: true,
            //分
            minutes: true,
            //秒
            seconds: true
          },
          //图标
          icons: {
            time: 'bi bi-clock',
            date: 'bi bi-calendar',
            up: 'bi bi-arrow-up',
            down: 'bi bi-arrow-down',
            previous: 'bi bi-chevron-left',
            next: 'bi bi-chevron-right',
            today: 'bi bi-calendar-check',
            clear: 'bi bi-trash',
            close: 'bi bi-x',
          },
          //视图底部按钮
          buttons: {
            today: true,
            clear: true,
            close: true,
          },
        }
      });

      let td7 = new tempusDominus.TempusDominus(document.getElementById('endTime'), {
        //本地化控制
        localization: {
          ...td_zh,//展开语法
          format: 'yyyy-MM-dd HH:mm:ss',
          //是否使用24小时制,比如3.15分会变成15.15
          // hourCycle: 'h24'
        },
        //布局控制
        display: {
          //视图模式(选择年份视图最先开始)
          viewMode: 'calendar',
          components: {
            //是否开启日历，false:则是时刻模式
            calendar: true,
            //支持年份选择
            year: true,
            //是否开启月份选择
            month: true,
            //支持日期选择
            date: true,
            //底部按钮中那个时刻选择是否启用，false则表示隐藏，下面三个需要该选项为true时有效
            clock: true,
            //时
            hours: true,
            //分
            minutes: true,
            //秒
            seconds: true
          },
          //图标
          icons: {
            time: 'bi bi-clock',
            date: 'bi bi-calendar',
            up: 'bi bi-arrow-up',
            down: 'bi bi-arrow-down',
            previous: 'bi bi-chevron-left',
            next: 'bi bi-chevron-right',
            today: 'bi bi-calendar-check',
            clear: 'bi bi-trash',
            close: 'bi bi-x',
          },
          //视图底部按钮
          buttons: {
            today: true,
            clear: true,
            close: true,
          },
        }
      });


      //事件监听设定起始时间为td7实例的选中时间
      td6.subscribe(tempusDominus.Namespace.events.change, (e) => {
        td7.updateOptions({
          restrictions: {
            minDate: e.date,
          },
        });
      });
      //事件监听设定起始时间为td6实例的选中时间
      td7.subscribe(tempusDominus.Namespace.events.change, (e) => {
        td6.updateOptions({
          restrictions: {
            maxDate: e.date,
          },
        });
      });

      //下拉框美化插件，原生的bootstrap它会调用系统的那个下拉菜单
      $('select').selectpicker();


    } else {
      top.location.replace('login.html');
    }
  })


}