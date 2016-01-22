(function () {
    'use strict';

    angular
        .module('app')
        .controller('AddUserController', AddUserController);

    AddUserController.$inject = ['UserService', '$location', '$scope', '$timeout'];
    function AddUserController(UserService, $location, $scope, $timeout) {
        var vm = this;

        vm.addUser = addUser;

        $scope.errors = [];
        $scope.hasError = false;

        function addUser() {
            UserService.Create(vm.user, function (status, data) {
                if (status == 200) {
                    UserService.GetAll();
                    $location.path('/');
                } else {
                    if (status == 401) {
                        $location.path('/login');
                    } else {
                        $scope.hasError = true;
                        $scope.errors.push(data);
                        $timeout(
                            function() {
                                scope.hasError = false;
                                scope.errors.remove();
                            }, 1500);
                    }
                }
            });
        };



    }
})();