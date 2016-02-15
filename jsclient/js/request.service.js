(function () {
    'use strict';

    angular
        .module('app')
        .factory('RequestService', RequestService);

    RequestService.$inject = ['$http', '$location', '$rootScope'];
    function RequestService($http, $location, $rootScope) {
        var service = {};

        service.GetAll = GetAll;
        service.GetById = GetById;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;

        return service;

        function GetAll(userId) {
            return $http.get($rootScope.restUrl + '/requests/' + userId).then(
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
            return $http.get($rootScope.restUrl + '/request/' + requestId)
                .then(handleSuccess, handleError('Error getting request by id'));
        }

        function Create(request, callback) {
            return $http.post($rootScope.restUrl + '/request/add', request)
                .success(function (data, status, header) {
                    callback(data, status, header);
                })
                .error(function(data, status) {
                    callback(data, status);
                });
        }

        function Update(user) {
            return $http.put($rootScope.restUrl + '/request/put', user)
                .then(handleSuccess, handleError('Error updating request'));
        }

        function Delete(id) {
            return $http.delete($rootScope.restUrl + '/request/delete/' + id)
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