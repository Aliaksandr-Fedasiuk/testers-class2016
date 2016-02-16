(function () {
    'use strict';

    angular
        .module('app')
        .controller('RequestController', RequestController);

    RequestController.$inject = ['RequestService', '$routeParams'];
    function RequestController(RequestService, $routeParams) {

        var vm = this;

        vm.request = null;
        vm.userId = $routeParams.userId;
        vm.allRequests = [];
        vm.loadAllRequests = loadAllRequests;
        vm.deleteRequest = deleteRequest;

        initController();

        function initController() {
            loadAllRequests();
        }

        function loadAllRequests() {
            RequestService.GetAll(vm.userId)
                .then(function (requests) {
                        vm.allRequests = requests;
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