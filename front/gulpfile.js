var gulp = require('gulp');
var sass = require('gulp-sass');
var rename = require('gulp-rename');

//Minifiers
var uglifyjs = require('gulp-uglify');
var uglifycss = require('gulp-uglifycss');
var htmlmin = require('gulp-htmlmin');

var config = {
    bootstrapDir: './node_modules/bootstrap-sass',
    jQueryDir: './node_modules/jquery',
    vueDir: './node_modules/vue',
    htmlDir: './src/html',
    imgDir: './src/img',
    publicDir: './dist'
};

gulp.task('css', function () {
    gulp.src('./src/scss/application.scss')
        .pipe(sass({includePaths: [config.bootstrapDir + '/assets/stylesheets']}))
        .pipe(gulp.dest(config.publicDir + '/css'))
        .pipe(uglifycss())
        .pipe(rename("application.min.css"))
        .pipe(gulp.dest(config.publicDir + '/css'));
});

gulp.task('fonts', function () {
    gulp.src(config.bootstrapDir + '/assets/fonts/**/*')
        .pipe(gulp.dest(config.publicDir + '/fonts'));
});

gulp.task('html', function () {
    gulp.src(config.htmlDir + '/**/*')
        .pipe(htmlmin({collapseWhitespace: true}))
        .pipe(gulp.dest(config.publicDir));
});

gulp.task('img', function () {
    gulp.src(config.imgDir + '/**/*')
        .pipe(gulp.dest(config.publicDir + '/img'));
});

gulp.task('js', function () {
    //Copying existing files
    gulp.src(config.jQueryDir + '/dist/jquery.min.js')
        .pipe(gulp.dest(config.publicDir + '/js'));
    gulp.src(config.bootstrapDir + '/assets/javascripts/bootstrap.min.js')
        .pipe(gulp.dest(config.publicDir + '/js'));
    gulp.src(config.vueDir + '/dist/vue.min.js')
        .pipe(gulp.dest(config.publicDir + '/js'));
});

gulp.task('default', ['html', 'img', 'css', 'fonts', 'js']);
