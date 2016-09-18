angular.module('app.main', ['ngRoute', 'app.main.templates', 'ui.bootstrap'])
    .config(function ($routeProvider) {
        'use strict';
        $routeProvider
            .when('/', {redirectTo: '/component-1/dialog-a'})
            .when('/component-1/dialog-a', {templateUrl: 'component-1/dialog-a/dialog-a.tpl.html'})
            .otherwise({templateUrl: 'main/page-not-found/page-not-found.html'});
    });
