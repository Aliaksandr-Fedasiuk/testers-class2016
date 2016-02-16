(function () {
    'use strict';

    angular
        .module('app')
        .controller('EditUserController', EditUserController);

    EditUserController.$inject = ['UserService', '$location', '$routeParams'];
    function EditUserController(UserService, $location, $routeParams) {

        var vm = this;
        vm.saveUser = saveUser;

        loadSelectedUser();

        function loadSelectedUser() {
            UserService.GetByUsername($routeParams.username)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function saveUser() {
            UserService.Update(vm.user);
            $location.path('/');
        };
    }
})();