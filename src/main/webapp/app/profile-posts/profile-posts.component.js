'use strict';

angular.
    module('myProfilePosts').
    component('myProfilePosts', {
        templateUrl: 'app/profile-posts/profile-posts.template.html',
        controller: ['$mdToast', '$routeParams', '$scope', 'JWToken', 'Image', 'Post', 'User',
            function ProfilePostsController($mdToast, $routeParams, $scope, JWToken, Image, Post, User) {
                var self = this;

                self.isLoggedIn = false;
                self.isProfileOwner = false;
                self.isPostDeleted = [];
                self.postCounter = 0;

                self.user = User.UserById.get({userId: $routeParams.userId});
                self.posts = Post.PostsBySubject.query({
                    subjectType: 'USER',
                    subjectId: $routeParams.userId,
                    getComments: true
                }, function()  {
                    self.postCounter = self.posts.length;
                    angular.forEach(self.posts, function(post, key) {
                        self.formatPostData(post, key);
                    });
                }, function() {
                    console.log("User " + $routeParams.userId + " has no posts.");
                });
                Image.ProfileImage.get({userId: $routeParams.userId}, function(imgRes) {
                    self.profileImage = imgRes;
                }, function() {
                    console.log("User " + $routeParams.userId + " has no profile image.");
                });

                JWToken.isLoggedIn().then(function(authResult) {
                    self.isLoggedIn = authResult;
                });

                JWToken.isOwner($routeParams.userId).then(function(res) {
                    self.isProfileOwner = res;
                });

                self.formatPostData = function(post, key) {
                    self.isPostDeleted[key] = false;
                    JWToken.isOwner(post.posterId).then(function(res) {
                        post.owner = res;
                    });
                    post.formattedCreationDate = (new Date(post.creationDate[0],
                                                           post.creationDate[1],
                                                           post.creationDate[2],
                                                           post.creationDate[3],
                                                           post.creationDate[4],
                                                           post.creationDate[5])).toLocaleString();

                    post.formattedLastEditDate = (new Date(post.lastEditDate[0],
                                                           post.lastEditDate[1],
                                                           post.lastEditDate[2],
                                                           post.lastEditDate[3],
                                                           post.lastEditDate[4],
                                                           post.lastEditDate[5])).toLocaleString();

                    post.poster = User.UserById.get({userId: post.posterId});
                    post.posterProfileImage = Image.ProfileImage.get({userId: post.posterId}, function(imgResult){
                        post.posterProfileImage = imgResult;
                    }, function() {
                        console.log("User " + post.posterId + " has no profile image.");
                    });
                }

                self.isPostOwner = function(id) {
                    return JWToken.isOwner(id);
                }

                self.deletePost = function(id, key) {
                    Post.DeletePost.delete({postId: id}, function() {
                        self.isPostDeleted[key] = true;
                        self.postCounter--;

                        console.log("Post with post ID: " + id + " was deleted successfully." + self.posts.length);
                    }, function() {
                        console.log("Post with post ID: " + id + " deletion failed.");
                    });
                }

                self.submitPost = function(isValid) {
                    if (isValid) {
                        var tkn = JWToken.getToken();
                        JWToken.getTokenBody(tkn).then(function(tknBodyRes) {
                            var tknBody = JSON.parse(tknBodyRes);

                            self.newPost = JSON.stringify({
                                        posterId:    tknBody.sub,
                                        subjectType: 'USER',
                                        subjectId:   $routeParams.userId,
                                        title :      $scope.title,
                                        content :    $scope.content
                            });

                            Post.AddPost.save(self.newPost, function(response) {
                                $scope.title = null;
                                $scope.content = null;
                                $scope.newPostForm.$setPristine();
                                $scope.newPostForm.$setUntouched();

                                self.posts.push(response);
                                self.formatPostData(self.posts[self.posts.length - 1], (self.posts.length - 1));
                                self.postCounter++;

                                console.log("Post submitted succesfully.");
                            }, function() {
                                $mdToast.show(
                                    $mdToast.simple()
                                      .textContent('Submitting new post failed...')
                                      .action('Dismiss')
                                      .highlightAction(true)
                                      .highlightClass('md-primary md-warn')
                                      .position('bottom center')
                                      .hideDelay(3000)
                                );
                                console.log("Posting failed.");
                            });
                        });
                    }
                };
        }]
    });
