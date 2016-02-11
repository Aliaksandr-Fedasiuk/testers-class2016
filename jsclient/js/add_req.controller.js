(function () {
    'use strict';

    angular
        .module('app')
        .controller('AddRequestController', AddRequestController);

    AddRequestController.$inject = ['RequestService', '$location', '$scope', 'FlashService'];
    function AddRequestController(RequestService, $location, $scope, FlashService) {

        var vm = this;

        vm.request = null;
        vm.addRequest = addRequest;

        $scope.errors = [];
        $scope.hasError = false;

        function addRequest() {
            vm.dataLoading = true;
            RequestService.Create(vm.request, function (status, data) {
                if (status == 200) {
                    $location.path('request/' + user.userId);
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