#include "mixedNumber.h"
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

bool getInput(MixedNumber &x, char &op, MixedNumber &y);
void perform(const MixedNumber &x, char op, const MixedNumber &y);

int main() {
    MixedNumber x, y;
    char op;
    while(getInput(x,op,y))
        perform(x, op, y);
    return 0;
}

bool getInput(MixedNumber &x, char &op, MixedNumber &y) {
    string line;
    cout<<"Input: ";
    getline(cin,line);
    if(line.empty())
        return false;
    stringstream store(line);
    store>>x>>op>>y;
    return true;
}

void perform(const MixedNumber &x, char op, const MixedNumber &y) {
    MixedNumber ans;
    switch(op) {
        case '+' : ans = x + y;
                   break;
        case '-' : ans = x - y;
                   break;
        case '*' : ans = x * y;
                   break;
        case '/' : ans = x / y;
                   break;
        default  : cout<<"Invalid Input."<<endl;
                   return;
    }
    cout<<x<<" "<<op<<" "<<y<<" = "<<ans<<endl;
}
