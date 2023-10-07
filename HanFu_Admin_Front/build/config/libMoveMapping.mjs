export let libMoveMapping = [
  {
    //从哪里复制
    form: 'node_modules/@eonasdan',
    //复制到哪里去
    to: 'lib/@eonasdan',
    //要删除的文件的匹配规则
    delGlobs:[
      'lib/@eonasdan/tempus-dominus/*',
      '!lib/@eonasdan/tempus-dominus/dist',
    ]
  },
  {
    form: 'node_modules/@popperjs',
    to: 'lib/@popperjs',
    delGlobs:[
      'lib/@popperjs/core/*',
      '!lib/@popperjs/core/dist',
    ]
  },
  {
    form: 'node_modules/@ztree',
    to: 'lib/@ztree',
    delGlobs:[
      'lib/@ztree/ztree_v3/*',
      '!lib/@ztree/ztree_v3/css',
      '!lib/@ztree/ztree_v3/js',


      'lib/@ztree/ztree_v3/css/demo.css',
    ]
  },
  {
    form: 'node_modules/bootstrap',
    to: 'lib/bootstrap',
    delGlobs:[
      'lib/bootstrap/*',
      '!lib/bootstrap/dist',
    ]
  },
  {
    form: 'node_modules/bootstrap-icons',
    to: 'lib/bootstrap-icons',
    delGlobs:[
      'lib/bootstrap-icons/*',
      '!lib/bootstrap-icons/font',
      'lib/bootstrap-icons/font/bootstrap-icons.scss',
    ]
  },
  {
    form: 'node_modules/bootstrap-quicktab',
    to: 'lib/bootstrap-quicktab',
    delGlobs:[
      'lib/bootstrap-quicktab/*',
      '!lib/bootstrap-quicktab/dist'
    ]
  },
  {
    form: 'node_modules/bootstrap-table',
    to: 'lib/bootstrap-table',
    delGlobs:[
      'lib/bootstrap-table/*',
      '!lib/bootstrap-table/dist'
    ]
  },
  {
    form: 'node_modules/chart.js',
    to: 'lib/chart.js',
    delGlobs:[
      'lib/chart.js/*',
      '!lib/chart.js/dist',

      'lib/chart.js/dist/*',
      '!lib/chart.js/dist/chart.umd.js',
      '!lib/chart.js/dist/chart.umd.js.map',
    ]
  },
  {
    form: 'node_modules/croppie',
    to: 'lib/croppie',
    delGlobs:[
      'lib/croppie/*',
      '!lib/croppie/croppie.css',
      '!lib/croppie/croppie.js',
      '!lib/croppie/croppie.min.js',
    ]
  },
  {
    form: 'node_modules/dropzone',
    to: 'lib/dropzone',
    delGlobs:[
      'lib/dropzone/*',
      '!lib/dropzone/dist',
    ]
  },
  {
    form: 'node_modules/echarts',
    to: 'lib/echarts',
    delGlobs:[
      'lib/echarts/*',
      '!lib/echarts/dist',
    ]
  },
  {
    form: 'node_modules/jquery',
    to: 'lib/jquery',
    delGlobs:[
      'lib/jquery/*',
      '!lib/jquery/dist',
    ]
  },
  {
    form: 'node_modules/moment',
    to: 'lib/moment',
    delGlobs:[
      'lib/moment/*',
      '!lib/moment/dist',
      '!lib/moment/locale',
      '!lib/moment/min',
    ]
  },
  {
    form: 'node_modules/select2',
    to: 'lib/select2',
    delGlobs:[
      'lib/select2/*',
      '!lib/select2/dist',
    ]
  },
  {
    form: 'node_modules/select2-bootstrap-5-theme',
    to: 'lib/select2-bootstrap-5-theme',
    delGlobs:[
      'lib/select2-bootstrap-5-theme/*',
      '!lib/select2-bootstrap-5-theme/dist',
    ]
  },

  {
    form: 'node_modules/sweetalert2',
    to: 'lib/sweetalert2',
    delGlobs:[
      'lib/sweetalert2/*',
      '!lib/sweetalert2/dist',
    ]
  },
  {
    form: 'node_modules/@fonticonpicker',
    to: 'lib/@fonticonpicker',
    delGlobs:[
      'lib/@fonticonpicker/fonticonpicker/*',
      '!lib/@fonticonpicker/fonticonpicker/dist',
    ]
  },
  {
    form: 'node_modules/bootstrap-fileinput',
    to: 'lib/bootstrap-fileinput',
    delGlobs:[
      'lib/bootstrap-fileinput/*',
      '!lib/bootstrap-fileinput/css',
      '!lib/bootstrap-fileinput/img',
      '!lib/bootstrap-fileinput/js',
      '!lib/bootstrap-fileinput/themes',
    ]
  },
  {
    form: 'node_modules/@wangeditor',
    to: 'lib/@wangeditor',
    delGlobs:[
      'lib/@wangeditor/**',
      '!lib/@wangeditor/editor',

      'lib/@wangeditor/editor/**',
      '!lib/@wangeditor/editor/dist',


      'lib/@wangeditor/editor/dist/**',
      '!lib/@wangeditor/editor/dist/css',
      '!lib/@wangeditor/editor/dist/index.js',
      '!lib/@wangeditor/editor/dist/index.js.map',
    ]
  },
  {
    form: 'node_modules/overlayscrollbars',
    to: 'lib/overlayscrollbars',
    delGlobs:[
      'lib/overlayscrollbars/**',
      '!lib/overlayscrollbars/browser',
      '!lib/overlayscrollbars/styles',

    ]
  },
  {
    form: 'node_modules/lightgallery',
    to: 'lib/lightgallery',
    delGlobs:[
      'lib/lightgallery/**',
      '!lib/lightgallery/css',
      '!lib/lightgallery/fonts',
      '!lib/lightgallery/plugins',
      '!lib/lightgallery/images',
      '!lib/lightgallery/lightgallery.min.js',


      'lib/lightgallery/plugins/zoom/**',
      '!lib/lightgallery/plugins/zoom/lg-zoom.min.js',

      'lib/lightgallery/plugins/vimeoThumbnail/**',
      '!lib/lightgallery/plugins/vimeoThumbnail/lg-vimeo-thumbnail.min.js',

      'lib/lightgallery/plugins/video/**',
      '!lib/lightgallery/plugins/video/lg-video.min.js',

      'lib/lightgallery/plugins/thumbnail/**',
      '!lib/lightgallery/plugins/thumbnail/lg-thumbnail.min.js',

      'lib/lightgallery/plugins/share/**',
      '!lib/lightgallery/plugins/share/lg-share.min.js',

      'lib/lightgallery/plugins/rotate/**',
      '!lib/lightgallery/plugins/rotate/lg-rotate.min.js',

      'lib/lightgallery/plugins/relativeCaption/**',
      '!lib/lightgallery/plugins/relativeCaption/lg-relative-caption.min.js',

      'lib/lightgallery/plugins/pager/**',
      '!lib/lightgallery/plugins/pager/lg-pager.min.js',

      'lib/lightgallery/plugins/mediumZoom/**',
      '!lib/lightgallery/plugins/mediumZoom/lg-medium-zoom.min.js',

      'lib/lightgallery/plugins/hash/**',
      '!lib/lightgallery/plugins/hash/lg-hash.min.js',

      'lib/lightgallery/plugins/fullscreen/**',
      '!lib/lightgallery/plugins/fullscreen/lg-fullscreen.min.js',

      'lib/lightgallery/plugins/comment/**',
      '!lib/lightgallery/plugins/comment/lg-comment.min.js',

      'lib/lightgallery/plugins/autoplay/**',
      '!lib/lightgallery/plugins/autoplay/lg-autoplay.min.js',
    ]
  },
  {
    form: 'node_modules/bootstrap-duallistbox',
    to: 'lib/bootstrap-duallistbox',
    delGlobs:[
      'lib/bootstrap-duallistbox/**',
      '!lib/bootstrap-duallistbox/dist',
    ]
  },
  {
    form: 'node_modules/jquery-treegrid',
    to: 'lib/jquery-treegrid',
    delGlobs:[
      'lib/jquery-treegrid/**',
      '!lib/jquery-treegrid/css',
      '!lib/jquery-treegrid/js',
      '!lib/jquery-treegrid/img',
    ]
  },
  {
    form: 'node_modules/bs-stepper',
    to: 'lib/bs-stepper',
    delGlobs:[
      'lib/bs-stepper/**',
      '!lib/bs-stepper/dist',
    ]
  },
  {
    form: 'node_modules/raty-js',
    to: 'lib/raty-js',
    delGlobs:[
      'lib/raty-js/**',
      '!lib/raty-js/lib',
    ]
  },
  {
    form: 'node_modules/bootstrap-input-spinner',
    to: 'lib/bootstrap-input-spinner',
    delGlobs:[
      'lib/bootstrap-input-spinner/**',
      '!lib/bootstrap-input-spinner/src',
    ]
  },
  {
    form: 'node_modules/@simonwep',
    to: 'lib/@simonwep',
    delGlobs:[
      'lib/@simonwep/pickr/*',
      '!lib/@simonwep/pickr/dist',
    ]
  },
  {
    form: 'node_modules/video.js',
    to: 'lib/video.js',
    delGlobs:[
      'lib/video.js/*',
      '!lib/video.js/dist',

      'lib/video.js/dist/*',
      '!lib/video.js/dist/alt',
      '!lib/video.js/dist/font',
      '!lib/video.js/dist/lang',
      '!lib/video.js/dist/*.js',
      '!lib/video.js/dist/*.css',
    ]
  },
  {
    form: 'node_modules/clipboard',
    to: 'lib/clipboard',
    delGlobs:[
      'lib/clipboard/*',
      '!lib/clipboard/dist',
    ]
  },
  {
    form: 'node_modules/dayjs',
    to: 'lib/dayjs',
    delGlobs:[
      'lib/dayjs/*',
      '!lib/dayjs/locale',
      '!lib/dayjs/plugin',
      '!lib/dayjs/dayjs.min.js',

      'lib/dayjs/plugin/*',
      '!lib/dayjs/plugin/*.js',
    ]
  },
  {
    form: 'node_modules/@fullcalendar',
    to: 'lib/@fullcalendar',
    delGlobs:[
      'lib/@fullcalendar/core/*',
      '!lib/@fullcalendar/core/locales',
      '!lib/@fullcalendar/core/index.global.js',
      '!lib/@fullcalendar/core/index.global.min.js',
      '!lib/@fullcalendar/core/locales-all.global.js',
      '!lib/@fullcalendar/core/locales-all.global.min.js',

      'lib/@fullcalendar/core/locales/*',
      '!lib/@fullcalendar/core/locales/*.global.js',
      '!lib/@fullcalendar/core/locales/*.global.min.js',


      'lib/@fullcalendar/daygrid/*',
      '!lib/@fullcalendar/daygrid/*.global.js',
      '!lib/@fullcalendar/daygrid/*.global.min.js',

      'lib/@fullcalendar/interaction/*',
      '!lib/@fullcalendar/interaction/*.global.js',
      '!lib/@fullcalendar/interaction/*.global.min.js',

      'lib/@fullcalendar/list/*',
      '!lib/@fullcalendar/list/*.global.js',
      '!lib/@fullcalendar/list/*.global.min.js',

      'lib/@fullcalendar/multimonth/*',
      '!lib/@fullcalendar/multimonth/*.global.js',
      '!lib/@fullcalendar/multimonth/*.global.min.js',


      'lib/@fullcalendar/timegrid/*',
      '!lib/@fullcalendar/timegrid/*.global.js',
      '!lib/@fullcalendar/timegrid/*.global.min.js',
    ]
  },
  {
    form: 'node_modules/datatables.net',
    to: 'lib/datatables.net',
    delGlobs:[
      'lib/datatables.net/*',
      '!lib/datatables.net/js',
    ]
  },
  {
    form: 'node_modules/datatables.net-bs5',
    to: 'lib/datatables.net-bs5',
    delGlobs:[
      'lib/datatables.net-bs5/*',
      '!lib/datatables.net-bs5/css',
      '!lib/datatables.net-bs5/images',
      '!lib/datatables.net-bs5/js',
    ]
  },
  {
    form: 'node_modules/shepherd.js',
    to: 'lib/shepherd.js',
    delGlobs:[
      'lib/shepherd.js/*',
      '!lib/shepherd.js/dist',
    ]
  },
  {
    form: 'node_modules/bootstrap-select',
    to: 'lib/bootstrap-select',
    delGlobs:[
      'lib/bootstrap-select/*',
      '!lib/bootstrap-select/dist',
    ]
  },
  {
    form: 'node_modules/jquery.cookie',
    to: 'lib/jquery.cookie',
    delGlobs:[
      'lib/jquery.cookie/*',
      '!lib/jquery.cookie/jquery.cookie.js',
    ]
  },
  {
    form: 'node_modules/countup.js',
    to: 'lib/countup.js',
    delGlobs:[
      'lib/countup.js/*',
      '!lib/countup.js/dist',
    ]
  },
  {
    form: 'build/formvalidation',
    to: 'lib/formvalidation',
    delGlobs:[]
  }
]

