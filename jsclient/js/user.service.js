(function () {
    'use strict';

    angular
        .module('app')
        .factory('UserService', UserService);

    UserService.$inject = ['$http', '$location', '$rootScope'];
    function UserService($http, $location, $rootScope) {
        var service = {};

        service.GetAll = GetAll;
        service.GetManagers = GetManagers;
        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;

        return service;

        function GetAll() {
            return $http.get($rootScope.restUrl + '/users').then(
                function(response) {
                    return handleSuccess(response);
                },
                function(data) {
                    handleError('Error getting all users');
                    if (data.statusText == "Unauthorized") {
                        $location.path('/login');
                    }
                }
            );
        }

        function GetById(userId) {
            return $http.get($rootScope.restUrl + '/user/' + userId)
                .then(handleSuccess, handleError('Error getting user by id'));
        }

        function GetByUsername(username) {
            return $http.get($rootScope.restUrl + '/user/' + username)
                .then(handleSuccess, handleError('Error getting user by username'));
        }

        function GetManagers(role) {
            return $http.get($rootScope.restUrl + '/managers/' + role)
                .then(handleSuccess, handleError('Error getting managers'));
        }

        function Create(user, callback) {
            $http.post($rootScope.restUrl + '/registration', user)
                .success(function (data, status, header) {
                    callback(data, status, header);
                })
                .error(function(data, status) {
                    callback(data, status);
                });
        }

        function Update(user) {
            return $http.put($rootScope.restUrl + '/user/put', user)
                .then(handleSuccess, handleError('Error updating user.'));
        }

        function Delete(id) {
            return $http.delete($rootScope.restUrl + '/user/delete/' + id)
                .then(handleSuccess, handleError('Error deleting user.'));
        }

        function handleSuccess(response) {
            return response.data;
        }

        function handleError(error) {
            return function () {
                return { success: false, message: error };
            }
        }
    }

})();