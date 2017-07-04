'use strict';

angular.
	module('myUserRegistration').
	component('myUserRegistration', {
		templateUrl: 'app/user-registration/user-registration.template.html',
		controller: ['User',
            function UserRegistrationController(User) {
                $(document).ready(function(){
                    
                    $("#username").focusout(function() {
                        var username = $(this).val();                        
                        if (username == '') {
                            $(this).css("border-color", "#FF0000");
                            $('#submit').attr('disabled', true);
                            $("#error_username").text("* Please choose a username.");
                        }
                        else if (username.length < 3) {
                            $(this).css("border-color", "#FF0000");
                            $('#submit').attr('disabled', true);
                            $("#error_username").text("* The username must be at least 3 characters long.");
                        }
                        else if (username.match(/[\W]/)) {
                            $(this).css("border-color", "#FF0000");
                            $('#submit').attr('disabled', true);
                            $("#error_username").text("* The username can only contain alphanumerical characters.");
                        }                         
                        else {  
                            var self = $(this);
                            var tmpUser = User.UserByUsername.get({username: username});
                            tmpUser.$promise.then(function(userResult) {
                                self.css("border-color", "#FF0000");
                                $('#submit').attr('disabled', true);
                                $("#error_username").text("* This username is already in use.");                          
                            }, function() {
                                self.css("border-color", "#2eb82e");
                                $('#submit').attr('disabled', false);
                                $("#error_username").text("");                          
                            });
                        }
                    });
                    
                    $("#name").focusout(function() {
                        if ($(this).val() == '') {
                            $(this).css("border-color", "#FF0000");
                            $('#submit').attr('disabled', true);
                            $("#error_name").text("* Please enter your first name.");
                        }
                        else {
                            $(this).css("border-color", "#2eb82e");
                            $('#submit').attr('disabled', false);
                            $("#error_name").text("");
                        }
                    });
                    
                    $("#lastName").focusout(function() {
                        if ($(this).val() == '') {
                            $(this).css("border-color", "#FF0000");
                            $('#submit').attr('disabled', true);
                            $("#error_lastName").text("* Please enter your last name.");
                        }
                        else {
                            $(this).css("border-color", "#2eb82e");
                            $('#submit').attr('disabled', false);
                            $("#error_lastName").text("");
                        }
                    });
                    
                    $("#dateOfBirth").focusout(function() {
                        if ($(this).val() == '') {
                            $(this).css("border-color", "#FF0000");
                            $('#submit').attr('disabled', true);
                            $("#error_dateOfBirth").text("* Please enter your date of birth.");
                        }
                        else {
                            $(this).css("border-color", "#2eb82e");
                            $('#submit').attr('disabled', false);
                            $("#error_dateOfBirth").text("");
                        }
                    });

                    $("#password").focusout(function() {
                        var pwd = $(this).val();
                        if (pwd == '') {
                            $(this).css("border-color", "#FF0000");
                            $('#submit').attr('disabled', true);
                            $("#error_password").text("* Please choose a password!");
                        }
                        else if (pwd.length < 8) {
                                $(this).css("border-color", "#FF0000");
                                $('#submit').attr('disabled', true);
                                $("#error_password").text("* The password should be at least 8 characters long.");
                                checkFailed = true;
                        }
                        else {
                            if (!pwd.match(/[A-Z]/) || !pwd.match(/[a-z]/) || !pwd.match(/\d/) || !pwd.match(/[\W]/)) {
                                $(this).css("border-color", "#FFFF00");
                                $('#submit').attr('disabled', false);
                                $("#warning_password").text("* Suggestion: your password should be mixed case with alphanumerical and symbol characters.");
                            }   
                            else {
                                $(this).css("border-color", "#2eb82e");
                                $('#submit').attr('disabled', false);
                                $("#warning_password").text(""); 
                                $("#error_password").text("");                               
                            }
                        }   
                    });
                    
                    $("#passwordRepeat").focusout(function() {
                        var passwordRepeat = $(this).val();
                        if (passwordRepeat == '') {
                            $(this).css("border-color", "#FF0000");
                            $('#submit').attr('disabled', true);
                            $("#error_passwordRepeat").text("* Please repeat your password!");
                        }
                        else if (passwordRepeat != $('#password').val()) {
                            $(this).css("border-color", "#FF0000");
                            $('#submit').attr('disabled', true);
                            $("#error_passwordRepeat").text("* Passwords don't match.");
                        }
                        else {
                            $(this).css("border-color", "#2eb82e");
                            $('#submit').attr('disabled', false);
                            $("#error_passwordRepeat").text("");
                        }
                    });

                    $("#submit").click(function() {                        
                        var user = JSON.stringify({
                                    username :    $('#username').val(),
                                    name :        $('#name').val(),
                                    surname :     $('#lastName').val(),
                                    dateOfBirth : $('#dateOfBirth').val(),
                                    password :    $('#password').val(),                                    
                                    phoneNo :     $('#phone').val(),
                                    address :     $('#address').val()
                        });

                        User.Register.save(user);
                    });             
            });
        }]
    });