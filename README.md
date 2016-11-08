# cs356-assignment-2 miniTweeter by Chu Yiu Cheung

miniTwitter.java contains the main method.

Some rules for generating user/group id:
  Group IDs always end in 0 (i.e. 1000, 2000, 3000).
  User IDs are set depending on the group they are under and the order of creation.
  Example: The first user created under Root(Group ID 1000) will get user ID 1001.
 
Items that are not functional:
  The Observer pattern is setup but I couldn't get it to work.
  The check positives function is setup but I couldn't get it to work.
  All other operations are fully functional.
