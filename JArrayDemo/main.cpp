#include <iostream>
#include <string>
#include "jarray.h"

using namespace std;

template <typename T>
void tester(JArray<T>& arr, const string& typeName);

template <typename T>
void twoDArrayTester(const JArray<T>& arr, const string& typeName);

int main() {
    try {
        JArray<int> iArr;
        JArray<double> dArr;
        JArray<char> cArr;
        JArray<char*> cpArr;
        JArray<string> sArr;
        tester(iArr, "Integer");
        tester(dArr, "Double");
        tester(cArr, "Character");
        tester(cpArr, "Character Pointer");
        tester(sArr, "String"); // malloc.. y?

        twoDArrayTester(iArr, "Integer");
        twoDArrayTester(dArr, "Double");
        twoDArrayTester(cArr, "Character");
        twoDArrayTester(cpArr, "Character Pointer");
        twoDArrayTester(sArr, "String");
    } catch(ERROR e) {
        cout<<"Error occured: ";
        if(e == OUT_OF_BOUND)
            cout<<"OUT OF BOUND";
        else if(e == BAD_SIZE)
            cout<<"BAD SIZE";
        else
            cout<<"NULL POINTER";
        cout<<endl;
    }
    cout<<"\n*************** FINISHED ***************"<<endl;
    return 0;
}

template <typename T>
void tester(JArray<T>& arr, const string& typeName) {
    cout<<"*************** "<<typeName<<" Array test ***************"<<endl;
    cout<<"INPUT: ";
    cin>>arr;
    cout<<"OUTPUT: "<<endl;
    cout<<"    Unsorted: "<<arr<<endl;
    arr.sort();
    cout<<"    Sorted: "<<arr<<endl;
}

template <typename T>
void twoDArrayTester(const JArray<T>& arr, const string& typeName) {
    size_t size;
    cout<<"*************** "<<typeName<<" 2D Array test ***************"<<endl;
    cout<<"Enter the size of 2D Array: ";
    cin>>size;
    JArray<JArray<T>> twoDArr(size);
    for(size_t i=0; i<size; ++i)
        twoDArr[i] = arr;
    cout<<twoDArr<<endl;
}
