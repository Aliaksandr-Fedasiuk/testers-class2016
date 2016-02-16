(function () {
    'use strict';

    angular
        .module('app')
        .controller('AddUserController', AddUserController);

    AddUserController.$inject = ['UserService', '$location', '$scope', '$timeout'];
    function AddUserController(UserService, $location, $scope, $timeout) {

        var vm = this;

        vm.managers = [];

        vm.addUser = addUser;
        vm.loadManagers = loadManagers;

        $scope.errors = [];
        $scope.hasError = false;

        initController();

        function initController() {
            loadManagers();
        }

        function loadManagers() {
            var role = 'ROLE_SUBORDINATE';
            if (vm.user != undefined) {
                role = vm.user.role;
            }
            UserService.GetManagers(role)
                .then(function (managers) {
                    vm.managers = managers;
                });
        }

        function addUser() {
            UserService.Create(vm.user, function (data, status) {
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