(function () {
    'use strict';

    angular
        .module('app')
        .controller('EditUserController', EditUserController);

    EditUserController.$inject = ['UserService', '$location', '$rootScope'];
    function EditUserController(UserService, $location, $rootScope) {

        var vm = this;
        vm.saveUser = saveUser;

        initController();

        function initController() {
            loadCurrentUser();
        }

        function loadCurrentUser() {
            UserService.GetByUsername($rootScope.globals.currentUser.username)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function saveUser() {
            UserService.Update(user);
        }
    }
})();