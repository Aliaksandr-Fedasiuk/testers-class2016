(function () {
    'use strict';

    angular
        .module('app')
        .factory('RequestService', RequestService);

    RequestService.$inject = ['$http', '$location'];
    function RequestService($http, $location) {
        var service = {};
        var serverUrl = 'http://localhost:8090/rest/v1';

        service.GetAll = GetAll;
        service.GetById = GetById;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;

        return service;

        function GetAll(userId) {
            return $http.get(serverUrl + '/requests/' + userId).then(
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

        function GetById(requestId) {
            return $http.get(serverUrl + '/request/' + requestId)
                .then(handleSuccess, handleError('Error getting request by id'));
        }

        function Create(user, callback) {
            return $http.post(serverUrl + '/request/add', user)
                .success(function (data, status, header, config) {
                    callback(status, data);
                })
                .error(function(data, status, header, config) {
                    callback(status, data);
                });
        }

        function Update(user) {
            return $http.put(serverUrl + '/request/put', user)
                .then(handleSuccess, handleError('Error updating request'));
        }

        function Delete(id) {
            return $http.delete(serverUrl + '/request/delete/' + id)
                .then(handleSuccess, handleError('Error deleting request'));
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