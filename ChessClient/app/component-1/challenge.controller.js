angular.module('app.component1').controller('ChallengeController', ['$scope', '$modal', 'ChallengeService', 'PlayerProfileService', 'SharedPlayerProfileService',
    function($scope, $modal, ChallengeService, PlayerProfileService, SharedPlayerProfileService) {
        'use strict';

        var getAllChallenges, openErrorModal;

        $scope.data = {
            form: {},
            isSentChallengesAvailable: false,
            isReceivedChallengesAvailable: false,
            isServerUnavailable: false,
            sentChallenges: [],
            receivedChallenges: [],
            idProfileCurrentPlayer: null,
            playerProfileAndStatistics: []
        };

        $scope.getSentChallenges = function() {
            ChallengeService.getSentChallenges($scope.data.playerProfileAndStatistics.idPlayerStatistics).then(function(response) {
                $scope.data.sentChallenges = response.data;
            }).then(function() {
                $scope.data.isServerUnavailable = false;
                if ($scope.data.sentChallenges.length === 0) {
                    $scope.data.isSentChallengesAvailable = false;
                    return;
                };
                $scope.data.isSentChallengesAvailable = true;
            }, function errorCallback() {
                $scope.data.isServerUnavailable = true;
                $scope.data.isSentChallengesAvailable = false;
            })
        };

        $scope.getReceivedChallenges = function() {
            ChallengeService.getReceivedChallenges($scope.data.playerProfileAndStatistics.idPlayerStatistics).then(function(response) {
                $scope.data.receivedChallenges = response.data;
            }).then(function() {
                $scope.data.isServerUnavailable = false;
                if ($scope.data.receivedChallenges.length === 0) {
                    $scope.data.isReceivedChallengesAvailable = false;
                    return;
                };
                $scope.data.isReceivedChallengesAvailable = true;
            }, function errorCallback() {
                $scope.data.isServerUnavailable = true;
                $scope.data.isReceivedChallengesAvailable = false;
            })
        };

        $scope.getPlayerProfileAndStatistics = function() {
            PlayerProfileService.getPlayerProfileAndStatistics($scope.data.idProfileCurrentPlayer).then(function(response) {
                $scope.data.playerProfileAndStatistics = response.data;
                SharedPlayerProfileService.setPlayerProfileAndStatistics($scope.data.playerProfileAndStatistics);
                getAllChallenges();
            }, function errorCallback(errorResponse) {
              openErrorModal(errorResponse);
            })
        };

        openErrorModal = function(response) {
          $modal.open({
              templateUrl: "/component-1/modal-dialog/error-modal.tpl.html",
              controller: "ErrorModalController",
              backdrop: true,
              keyboard: true,
              backdropClick: true,
              size: 'lg',
              resolve: {
                provideResponse: function() {
                  return response;
                }
              }
          });
        }

        $scope.setPlayerProfileId = function() {
            $scope.getPlayerProfileAndStatistics();
        };

        getAllChallenges = function() {
          $scope.getSentChallenges();
          $scope.getReceivedChallenges();
        };

        $scope.acceptChallenge = function(index) {
          ChallengeService.acceptChallenge($scope.data.receivedChallenges[index]).then(function() {
            $scope.getReceivedChallenges();
          }, function errorCallback(response) {
            openErrorModal(response);
            $scope.getReceivedChallenges();
          });
        };

        $scope.declineChallenge = function(index) {
          ChallengeService.declineChallenge($scope.data.receivedChallenges[index]).then(function() {
            $scope.getReceivedChallenges();
          }, function errorCallback(response) {
            openErrorModal(response);
            $scope.getReceivedChallenges();
          });
        };

        return {
          getPlayerProfileAndStatistics: function() {
            return $scope.data.playerProfileAndStatistics;
          }
        }
}]);
