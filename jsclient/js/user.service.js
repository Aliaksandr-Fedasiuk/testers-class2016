(function () {
    'use strict';

    angular
        .module('app')
        .factory('UserService', UserService);

    UserService.$inject = ['$http', '$location'];
    function UserService($http, $location) {
        var service = {};

        service.GetAll = GetAll;
        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;

        return service;

        function GetAll() {
            return $http.get('http://localhost:8090/rest/v1/users').then(
                function(response) {
                    console.log('success', response);
                    return handleSuccess(response);
                },
                function(data) {
                    console.log('error', data);
                    handleError('Error getting all users')
                    if (data.statusText == "Unauthorized") {
                        $location.path('/login');
                        console.log($location.path());
                    }
                }
            );
        }

        function GetById(id) {
            return $http.get('http://localhost:8090/rest/v1/user/' + userId).then(handleSuccess, handleError('Error getting user by id'));
        }

        function GetByUsername(username) {
            return $http.get('http://localhost:8090/rest/v1/user/' + username)
                .then(handleSuccess, handleError('Error getting user by username'));
        }

        function Create(user) {
            return $http.post('http://localhost:8090/rest/v1/user/add', user)
                .then(handleSuccess, handleError('Error creating user'));
        }

        function Update(user) {
            return $http.put('http://localhost:8090/rest/v1/user/put', user)
                .then(handleSuccess, handleError('Error updating user'));
        }

        function Delete(id) {
            return $http.delete('/api/users/' + id).then(handleSuccess, handleError('Error deleting user'));
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