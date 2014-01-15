'use strict';

/* Services */

// Posts service
var services = angular.module('blogrApp.services', ['ngResource']);

// Posts service
services.factory('PostsService', ['$resource',
    function ($resource) {
        return $resource('api/posts/:postId', {}, {
        })
    }]);

// creole parser http://github.com/codeholic/jscreole
services.value("creoleParser", function (data, element) {
    var creole = new window.creole({
        interwiki: {
            MeatballWiki: 'http://www.usemod.com/cgi-bin/mb.pl?',
            TiddlyWiki: 'http://www.tiddlywiki.com/#',
            WikiCreole: 'http://www.wikicreole.org/wiki/',
            Palindrome: function (link) {
                return 'http://www.example.com/wiki/' + link.split('').reverse().join('');
            }
        },
        linkFormat: '#'
    });

    creole.parse(element, data);
});