INSERT INTO User (name, pass, roles) values (
  'admin',
  'cb338f87d225df5877e9dd786eb874b4d6169393a03491d40a59ab468f7c57676e8e6651f0b3c76ce1ede50be1cef1c4f6ce0466e57e74b0622b1fa4fc1e1b8e',
  'admin,user');
INSERT INTO User (name, pass, roles) values (
  'editor',
  '3c9a5b48c8d6f513062fe77e56f94e0d068833b45911fea4745477283bf7471acc2430a6d39b37000713771991a170ec2677da6b5b217efa63a9c67e5728a12d',
  'editor,writer,reviewer,user');
INSERT INTO User (name, pass, roles) values (
  'writer',
  '33151555630292d7f640755fea996f94b9b8632fd4c0ed0ec4a10e5d3f1a5c29699873909f531664cf5da48e5a987b442a0f776293fe29475efab7939200b756',
  'writer,reviewer,user');
INSERT INTO User (name, pass, roles) values (
  'reviewer',
  'd469a8855e1e5d438945390046f2fd7f64cfd95fdd6ff58a3f1b478b7f62d14864338f05fc28f67b8aa15b079d8be0b93e14ecf64b1c6e1f9ff533b87f132617',
  'reviewer,user');
INSERT INTO Salt (salt) values ('asdf09we87r-21263sdflxcmn29473456y^)^&%sefaskdfsjgfd61031lsdhfasd');
