(function () {
    'use strict';

    angular
        .module('app')
        .controller('AddRequestController', AddRequestController);

    AddRequestController.$inject = ['RequestService', '$location', '$routeParams', '$scope', 'FlashService'];
    function AddRequestController(RequestService, $location, $routeParams, $scope, FlashService) {

        var vm = this;

        vm.request = {};
        vm.request.description = null;
        vm.request.userId = $routeParams.userId;
        vm.addRequest = addRequest;

        $scope.errors = [];
        $scope.hasError = false;

        function addRequest() {
            vm.dataLoading = true;
            RequestService.Create(vm.request, function (data, status) {
                if (status == 200) {
                    $location.path('/requests/' + $routeParams.userId);
                } else {
                    if (status == 401) {
                        $location.path('/login');
                    } else {
                        FlashService.Error(data, false);
                        vm.dataLoading = false;
                    }
                }
            });
        };
    }
})();