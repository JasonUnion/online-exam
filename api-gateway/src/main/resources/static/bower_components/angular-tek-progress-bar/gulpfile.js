var gulp = require('gulp');
var karmaServer = require('karma').Server;
var plugins = require('gulp-load-plugins')();
var browserSync = require('browser-sync');
var reload = browserSync.reload;

var config = {
    source: ["src/*.module.js", "src/**/*.js"],
    dest: {
        min: "tek.progress-bar.min.js",
        normal: "tek.progress-bar.js",
        dir: "dist"
    },
    karma: {
        config: "karma.conf.js"
    }
};

function inc(importance) {
    return gulp.src(['./package.json', './bower.json'])
        .pipe(plugins.bump({type: importance}))
        .pipe(gulp.dest('./'))
        .pipe(plugins.git.commit('bumps package version'))
        .pipe(plugins.filter('package.json'))
        .pipe(plugins.tag_version());
}

gulp.task('patch', function() { return inc('patch'); });
gulp.task('feature', function() { return inc('minor'); });
gulp.task('release', function() { return inc('major'); });

// watch files for changes and reload
gulp.task('serve', function() {
    browserSync({
        server: {
            baseDir: './examples/',
            index: 'index.html',
            routes: {
                "/bower_components": "bower_components",
                "/examples": "examples",
                "/src": "src"
            }
        }
    });

    gulp.watch([
        'examples/**/*.html',
        'examples/**/*.js',
        'examples/**/*.css',
        'src/**/*.js',
        'src/**/*.html'
    ], {cwd: './'}, reload);
});


gulp.task('clean', function () {
    return gulp.src('dist', {read: false})
        .pipe(plugins.clean());
});

gulp.task('minify', function () {
    var pkg = require('./package.json');
    var banner = ['/**',
        ' * <%= pkg.name %> - <%= pkg.description %>',
        ' * @version v<%= pkg.version %>',
        ' * @link <%= pkg.homepage %>',
        ' * @license <%= pkg.license %>',
        ' */',
        ''].join('\n');


    return gulp.src(config.source)
        // combine all source into one file
        .pipe(plugins.concat(config.dest.normal))
        .pipe(plugins.header(banner, { pkg : pkg } ))
        // write max version
        .pipe(gulp.dest(config.dest.dir))
        // build and write min version
        .pipe(plugins.sourcemaps.init())
        .pipe(plugins.uglify())
        // rename the file
        .pipe(plugins.rename(config.dest.min))
        .pipe(plugins.header(banner, { pkg : pkg } ))
        // before writing the map (this splits the stream)
        .pipe(plugins.sourcemaps.write("./"))
        .pipe(gulp.dest(config.dest.dir))
});


gulp.task('build', function (done) {
    plugins.runSequence('clean', ['minify'], done);
});

gulp.task('test', function (done) {
    return new karmaServer({
        configFile: __dirname + '/karma.conf.js',
        singleRun: true
    }, done).start();
});

gulp.task('default', function() {
    plugins.runSequence('test', 'build');
});