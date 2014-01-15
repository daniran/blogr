'use strict';

// main app
var blogrApp = angular.module('blogrApp', [
    'ngRoute',
    'blogrApp.filters',
    'blogrApp.services',
    'blogrApp.directives',
    'blogrApp.controllers'
]);

// constants
blogrApp.constant('POST_FETCH_LIMIT', 10);

// init
blogrApp.config(['$routeProvider', function ($routeProvider) {

    // blog list
    $routeProvider.when('/list', {templateUrl: 'partials/list.html', controller: 'BlogListController'});

    // view blog
    $routeProvider.when('/read/:postId', {templateUrl: 'partials/read.html', controller: 'BlogReadController'});

    // edit blog
    $routeProvider.when('/edit/:postId?', {templateUrl: 'partials/edit.html', controller: 'BlogEditController'});

    $routeProvider.otherwise({redirectTo: '/list'});
}]);
