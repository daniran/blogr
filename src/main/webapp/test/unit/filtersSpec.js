'use strict';

/* jasmine specs for filters go here */

describe('test filters', function () {
    beforeEach(module('blogrApp.filters'));

    describe('test postDate directive', function () {
        it('should parse timestamp', function () {
            inject(function ($interpolate, $rootScope) {
                $rootScope.timestamp = "2014-01-15T17:46:58.000+0000";
                var html = $interpolate('<p>{{ timestamp | postDate }}</p>')($rootScope);
                expect(html).toEqual("<p>Jan 15, 2014 7:46:58 PM</p>");
            });
        });
    });
});
