'use strict';

angular.
    module('myProfileHobbies').
    component('myProfileHobbies', {
        templateUrl: 'app/profile-hobbies/profile-hobbies.template.html',
        controller: ['$routeParams', 'Hobby', 'User',
            function ProfileHobbiesController($routeParams, Hobby, User) {
                var self = this;
                self.userHobbies;

                self.getUserHobbies = function() {
                    self.userHobbies = Hobby.UserHobbies.query({userId: $routeParams.userId}, function(hobbiesResult) {
                        self.userHobbies = hobbiesResult;
                    }, function() {
                        console.log("User " + $routeParams.userId + " has no hobbies.");
                    });
                }

                self.getUserHobbies();
                self.user = User.UserById.get({userId: $routeParams.userId});
        }]
    });
