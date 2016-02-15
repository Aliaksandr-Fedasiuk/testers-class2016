(function () {
    'use strict';

    angular
        .module('app')
        .controller('RequestController', RequestController);

    RequestController.$inject = ['RequestService', '$location', '$routeParams'];
    function RequestController(RequestService, $location, $routeParams) {

        var vm = this;

        vm.request = null;
        vm.userId = null;
        vm.allRequests = [];
        vm.loadAllRequests = loadAllRequests;
        vm.deleteRequest = deleteRequest;

        initController();

        function initController() {
            loadAllRequests();
        }

        function loadAllRequests() {
            RequestService.GetAll($routeParams.userId)
                .then(function (requests) {
                        vm.allRequests = requests;
                        vm.userId = $routeParams.userId;
                    }
                );
        }

        function deleteRequest(id) {
            RequestService.Delete(id)
                .then(function () {
                    loadAllRequests();
                });
        }
    }
})();