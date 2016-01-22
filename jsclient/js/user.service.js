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
        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;

        return service;

        function GetAll() {
            return $http.get(serverUrl + '/users').then(
                function(response) {
                    console.log('success', response);
                    return handleSuccess(response);
                },
                function(data) {
                    console.log('error', data);
                    handleError('Error getting all users')
                    if (data.statusText == "Unauthorized") {
                        $location.path('/login');
                    }
                }
            );
        }

        function GetById(id) {
            return $http.get(serverUrl + '/user/' + userId).then(handleSuccess, handleError('Error getting user by id'));
        }

        function GetByUsername(username) {
            return $http.get(serverUrl + '/user/' + username)
                .then(handleSuccess, handleError('Error getting user by username'));
        }

        function Create(user, callback) {
            return $http.post(serverUrl + '/user/add', user)
                .success(function (data, status, header, config) {
                    callback(status, data);
                })
                .error(function(data, status, header, config) {
                    callback(status, data);
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

        // private functions
        function handleSuccess(response) {
            return response.data;
        }

        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
    }

})();