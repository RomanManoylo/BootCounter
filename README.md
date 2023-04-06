NEED TO DO:
1. Handle notification permission for API 33
2. Add NotificationService and move from NotificationWorker stuff with notifications
3. Update method getAll boot – we don't need all list only at least 2
4. Prevent running multiple NotificationWorker at the same time
5. Add small test for delta between boot
6. Need to test cold start after first reboot
7. Clean up code and add parameters for string resources
8. For better user experience update UI  (use RecycleView for displaying list of boots timestamp)
9. Investigate optional task