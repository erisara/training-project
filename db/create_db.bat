@echo off

pushd %~dp0

echo Creating users...
sqlite3 TrainingApp.db ".read sql/users.sql"
echo Creating accounts...
sqlite3 TrainingApp.db ".read sql/accounts.sql"
echo Creating hobbies...
sqlite3 TrainingApp.db ".read sql/hobbies.sql"
echo Creating user_education...
sqlite3 TrainingApp.db ".read sql/user_education.sql"
echo Creating user_images...
sqlite3 TrainingApp.db ".read sql/user_images.sql"
echo Creating user_hobbies...
sqlite3 TrainingApp.db ".read sql/user_hobbies.sql"
echo Creating comments...
sqlite3 TrainingApp.db ".read sql/comments.sql"
echo Creating comment_replies...
sqlite3 TrainingApp.db ".read sql/comment_replies.sql"
echo Creating METRICS...
sqlite3 TrainingApp.db ".read sql/METRICS.sql"
echo Creating USER_SELECTED_DATES...
sqlite3 TrainingApp.db ".read sql/USER_SELECTED_DATES.sql"

popd

pause
