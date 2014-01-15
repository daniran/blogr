'use strict';

/* Services */

// Posts service
angular.module('blogrApp.services', ['ngResource']).
    value('version', '0.1').
    factory('Post', ['$resource',
        function ($resource) {
            return $resource('api/posts/:postId', {}, {
            })
        }]);
