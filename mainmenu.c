#include "header.h"


int main(int argc,char **argv){

  int ret;
  DB *db;
  DBC *dbcp;
  DBT key, data;
  char id[6];
  char name[40];

  db_create(&db, NULL, 0);
  db->set_flags(db, DB_DUP);
  db->open(db, NULL, DATABASE, NULL, DB_HASH, DB_CREATE, 0664);
  
  memset(&key, 0, sizeof(key));
  memset(&data, 0, sizeof(data));

  strcpy(id, "12345");
  strcpy(name, "John");

  key.data = id;
  key.size = (1 + strlen(id)*sizeof(char));

  data.data = name;
  data.size = (1 + strlen(name) * sizeof(char));
  //name[5] = '\0';
  db->put(db, NULL, &key, &data, 0);
  db->get(db, NULL, &key, &data, 0);
  printf("name=%s \n", (char * ) data.data);
  
  db->cursor(db, NULL, &dbcp, 0);
  
  ret = dbcp->c_get(dbcp, &key, &data, DB_SET);
  //code used to find duplicates of data object in db
  while(ret != DB_NOTFOUND) {
    printf("key: %s, data: %s", (char *)key.data, (char *) data.data);
    ret = dbcp->c_get(dbcp, &key, &data, DB_NEXT_DUP);
  }

  if(argc != 2){
    fprintf(stderr, "Error, incorrect command line arguments provided.\n Please enter type 'mydbtest db_type_option'\n");
    abort();}
  printf("Welcome to the Main Menu!\n");
  printf("Please enter the corresponding number of the option you would like to access:\n");
  printf("\n");
  printf("1. Create and populate the database\n");
  printf("2. Retrieve records with a given key\n");
  printf("3. Retrieve records with a given data\n");
  printf("4. Retrieve records within a given range of key values\n");
  printf("5. Destroy the database\n");
  printf("6. Quit\n");
  char input[10];
  int option;
  fgets(input,sizeof(input),stdin);
  sscanf(input,"%d",&option);
  switch(option){
  case 1:
    //call populate function
	
    break;
  case 2:
    //call retrieve records by key function
    break;
  case 3:
    //call retrieve record by data function
    break;
  case 4:
    //call retrieve by range function
    break;
  case 5: 
    //call destroy function
    break;
  case 6:
    //call quit & drop function
    break;
  default:
    printf("Error! Invalid input!/n");
    abort();
  }
  db->close(db,0);
  return 0;
}
