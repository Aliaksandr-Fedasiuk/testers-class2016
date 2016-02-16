(function () {
    'use strict';

    angular
        .module('app')
        .controller('EditRequestController', EditRequestController);

    EditRequestController.$inject = ['RequestService', '$location', '$routeParams', '$scope', '$rootScope', 'FlashService'];
    function EditRequestController(RequestService, $location, $routeParams, $scope, $rootScope, FlashService) {

        var vm = this;

        vm.request = {};
        vm.request.description = null;
        vm.request.userId = null;
        vm.editRequest = editRequest;

        $scope.errors = [];
        $scope.hasError = false;

        initController();

        function initController() {
            RequestService.GetById($routeParams.requestId)
                .then(function (request) {
                    vm.request = request;
                });
        }

        function editRequest() {
            vm.dataLoading = true;
            RequestService.Update(vm.request, function (data, status) {
                if (status == 200) {
                    $location.path('/requests/' + vm.request.userId);
                } else {
                    if (status == 401) {
                        $location.path('/login');
                    } else {
                        FlashService.Error(data, false);
                        vm.dataLoading = false;
                    }
                }
            });
        }
    }
})();