(function () {
    'use strict';

    angular.module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                controller: 'HomeController',
                templateUrl: 'home.view.html',
                controllerAs: 'vm'
            })

            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'login.view.html',
                controllerAs: 'vm'
            })

            .when('/add', {
                controller: 'AddUserController',
                templateUrl: 'add.view.html',
                controllerAs: 'vm'
            })

            .when('/edit/:username', {
                controller: 'EditUserController',
                templateUrl: 'edit.view.html',
                controllerAs: 'vm'
            })

            .when('/register', {
                controller: 'RegisterController',
                templateUrl: 'register.view.html',
                controllerAs: 'vm'
            })

            .when('/requests/:userId', {
                controller: 'RequestController',
                templateUrl: 'request.view.html',
                controllerAs: 'vm'
            })

            .when('/request/add/:userId', {
                controller: 'AddRequestController',
                templateUrl: 'addReq.view.html',
                controllerAs: 'vm'
            })

            .when('/request/edit/:requestId', {
                controller: 'EditRequestController',
                templateUrl: 'editReq.view.html',
                controllerAs: 'vm'
            })

            .otherwise({ redirectTo: '/login' });

    }

    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
    function run($rootScope, $location, $cookieStore, $http) {
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['x-authorization'] = $rootScope.globals.currentUser.authdata;
        }

        $http.get('connection.properties').then(function (response) {
            $rootScope.restUrl = response.data.restUrl;
        });

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });
    }
})();