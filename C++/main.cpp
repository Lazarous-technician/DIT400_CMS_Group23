#include <iostream>
#include <fstream>
#include <string>

using namespace std;

void Options();
void Registration();
void Login();
void options();

int main(){

   Options();

    int options;
    int total = 0;



    cout<< "Select an option: ";

    cin>>options;

    system("cls");

    switch(options){
    case 1:
        Login();
        break;
    case 2:
        Registration();
        break;
    default:
        cout<< "invalid inputs"<<endl;

    }

system("pause>0");

return 0;
}


 void Options(){
     cout<<"\n************* WELCOME TO COURSE MANAGEMENT SYSTEM *************\n"<<endl;

     cout<<"1. Login"<<endl;

     cout<<"2. Register"<<endl;


}


void Registration(){

    int student_id,file_id;

    string password, pass_file;

    bool exist = false;

    // collecting registration details from the users

    cout<< "Enter your student Id: ";
    cin>>student_id;
    cout<< "Enter the password: ";
    cin>>password;


    //opening the file
    ifstream usersData("users.txt");


    if(usersData.is_open()){

            while(usersData>>file_id >> pass_file){

                    if(file_id == student_id){

                        exist = true;
                        break;
                    }
            }
    }

    usersData.close();

    if(exist){

         cout << "This student ID " << student_id << " is already registered.\n";
    }else{

        ofstream users("users.txt", ios::app);

        users<<student_id<< " " <<password<<endl;

        users.close();

        system("cls");
        cout<< "Registration is success"<<endl;


    }

}


void options(){
    int CourseOption;
    string courseID;


    cout << "\n--- Course Options ---\n";

    cout << "1. Add Course\n";
    cout << "2. Delete Course\n";
    cout << "3. Search Course\n";
    cout << "4. Update Course\n";
    cout << "5. List All Courses\n";
    cout << "6. Logout / Exit\n"<<endl;

    cout<< "Select an Option: ";
    cin>>CourseOption;


    }



void Login(){

    string student_id;

    string password, file_id, file_pass;

    bool success = false;

    cout<< "Enter your student_id: ";
    cin>>student_id;
    cout<< "Enter your password: ";
    cin>>password;

    ifstream LoginData("users.txt");

    if(!LoginData){

        cout<< "Error:something went wrong "<<endl;
    }


    while(LoginData>>file_id>>file_pass){

        if(file_id == student_id && file_pass == password){

                success=true;
                break;
        }
    }


    LoginData.close();

    system("cls");

    if(success){

        cout << "\n **************Login successful! Welcome ************* " <<endl;

        options();

    }else{

        cout << "Login failed! Invalid ID or password.\n";
    }


}



