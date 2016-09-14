angular.module('app.component2', ['ngRoute', 'app.component2.templates', 'app.component1'])
    .config(function ($routeProvider) {
        'use strict';
        $routeProvider.when('/component-2/dialog-b', {
            templateUrl: 'component-2/dialog-b/dialog-b.html',
            controller: 'FilterBookController'
        });
    });

    // angular.module('app.component2', ['ngRoute', 'app.component2.templates'])
    //     .config(function ($routeProvider) {
    //         'use strict';
    //         $routeProvider.when('/component-2/dialog-b', {
    //             templateUrl: 'component-2/dialog-b/dialog-b.html',
    //             controller: 'DialogBController',
    //             resolve: {
    //               books: function($http) {
    //                 return $http.get('/component-1/books.json');
    //               }
    //             }
    //         });
    //     });
