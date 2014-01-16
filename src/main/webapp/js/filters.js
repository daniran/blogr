'use strict';

/* Filters */

var filters = angular.module('blogrApp.filters', []);

// timestamp filter
filters.filter('postDate', function ($filter) {
    return function (timestamp) {
        if (!timestamp)
            return '';
        return $filter('date')(timestamp, 'medium');
    }
});
