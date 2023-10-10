import gulp from 'gulp';
import * as dartSass from 'sass'
import gulpSass from 'gulp-sass';
import postcss from 'gulp-postcss';
import gulpStylelint from '@ronilaukkarinen/gulp-stylelint';
import autoprefixer from "autoprefixer";
import cmq from 'node-css-mqpacker';
import imagemin, {mozjpeg, gifsicle, svgo} from 'gulp-imagemin';
import pngquant from 'imagemin-pngquant'
import {deleteAsync} from 'del';
import cleanCss from 'gulp-clean-css'
import sourcemaps from 'gulp-sourcemaps'
import rename from 'gulp-rename'
import fs from 'fs-extra'


//lib目录自动化移动和清理目录结构(可以避免手动把插件从node_modules里面复制出来)
import {libMoveMapping} from "./build/config/libMoveMapping.mjs";


//sass
const sass = gulpSass(dartSass);
//postcss插件
const postcssPlugins = [
  autoprefixer(), cmq({
    sort: function (a, b) {
      let aMax = a.match(/\d+/)[0];
      let bMax = b.match(/\d+/)[0];
      return bMax - aMax;
    }
  })
];





gulp.task('lint-css', function () {
  return gulp.src('build/scss/**/*.scss')
    .pipe(gulpStylelint({
      //打印错误堆栈跟踪
      debug: false,
      //报错后是否直接终止程序
      failAfterError: true,
      //报错类型和格式处理
      reporters: [
        {formatter: 'string', console: true}
      ]
    }))
})


gulp.task('css', function () {
  return gulp.src('build/scss/**/*.scss')
    .pipe(sourcemaps.init({debug: true}))
    .pipe(sass.sync({
      outputStyle: "expanded"
    }).on('error', sass.logError))
    .pipe(postcss(postcssPlugins))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest('dist/css'))
});


gulp.task('css_min', function () {
  return gulp.src('build/scss/**/*.scss')
    .pipe(sourcemaps.init({debug: true}))
    .pipe(sass.sync({
      outputStyle: "expanded"
    }).on('error', sass.logError))
    .pipe(postcss(postcssPlugins))
    .pipe(cleanCss())
    .pipe(rename({suffix: '.min'}))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest('dist/css'))
});



gulp.task('img', function (done) {
  gulp.src('build/img/*')
    .pipe(imagemin([
      gifsicle({interlaced: true}),
      mozjpeg({quality: 75, progressive: true}),
      pngquant(),
      svgo({
        plugins: [
          {removeViewBox: true},
          {cleanupIDs: false}
        ]
      })
    ], {
      verbose: true
    }))
    .pipe(gulp.dest('dist/img'));
  done();
})




gulp.task('lib', async function (cb) {
  libMoveMapping.map(function (file) {
    fs.copy(file.form, file.to)
    .then(async function () {
      const deletedFilePaths = await deleteAsync(file.delGlobs, {dot: true});
      console.log('lib依赖目录脏目录删除:\n', deletedFilePaths.join('\n'));
    })
    .catch(err => console.error(err))
  })
  cb();
});


//负责把build/js/app.js移动到dist/js目录,因此我们修改任何代码都是应该修改build目录里的代码,dist目录都是可以随时生成的。
gulp.task('app.js', function () {
  return gulp.src('build/js/app.js').pipe(gulp.dest('dist/js'))
})


//用于在可能的情况下自动修复规则报告的问题
gulp.task('style:fix', function () {
  return gulp
    .src([
      'build/scss/**/*.scss',
      //除了plugins-override不需要修复
      '!build/scss/plugins-override/*.scss',
    ])
    .pipe(gulpStylelint({
      fix: true,
    }))
    .pipe(gulp.dest('build/scss'));
});


//打包样式(通过stylelint对样式进行检查是否通过  然后是打包bootstrap-admin.js 和 bootstrap-admin.min.js)
gulp.task('style', gulp.series(['lint-css', 'css','css_min']));

//这个是执行不加任何参数的gulp指令触发的任务
gulp.task('default', gulp.series(['style', 'img', 'lib']));


gulp.task("dev", function (cb) {
  gulp.watch(['build/scss/**/*.scss'], gulp.series('css'));
  cb()
});







