(function () {
    'use strict';

    angular
        .module('app')
        .factory('UserService', UserService);

    UserService.$inject = ['$http', '$location'];
    function UserService($http, $location) {
        var service = {};
        var serverUrl = 'http://localhost:8090/rest/v1';

        service.GetAll = GetAll;
        service.GetManagers = GetManagers;
        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;

        return service;

        function GetAll() {
            return $http.get(serverUrl + '/users').then(
                function(response) {
                    return handleSuccess(response);
                },
                function(data) {
                    handleError('Error getting all users')
                    if (data.statusText == "Unauthorized") {
                        $location.path('/login');
                    }
                }
            );
        }

        function GetById(userId) {
            return $http.get(serverUrl + '/user/' + userId)
                .then(handleSuccess, handleError('Error getting user by id'));
        }

        function GetByUsername(username) {
            return $http.get(serverUrl + '/user/' + username)
                .then(handleSuccess, handleError('Error getting user by username'));
        }

        function GetManagers(role) {
            return $http.get(serverUrl + '/managers/' + role)
                .then(handleSuccess, handleError('Error getting managers'));
        }

        function Create(user, callback) {
            $http.post(serverUrl + '/registration', user)
                .success(function (data, status, header) {
                    callback(data, status, header);
                })
                .error(function(data, status) {
                    callback(data, status);
                });
        }

        function Update(user) {
            return $http.put(serverUrl + '/user/put', user)
                .then(handleSuccess, handleError('Error updating user'));
        }

        function Delete(id) {
            return $http.delete(serverUrl + '/user/delete/' + id)
                .then(handleSuccess, handleError('Error deleting user'));
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