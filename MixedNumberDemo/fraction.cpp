#include "fraction.h"

// Private
int Fraction::gcd(const int& n, const int& d) const {
    return d ? gcd(d, n%d) : n;
}

void Fraction::simplify() {
    int div = gcd(num,denom);;
    num /= div;
    denom /= div;

    if(denom < 0) {
        num *= -1;
        denom *= -1;
    }
}

void Fraction::convert(const string& number) {
    stringstream store(number);
    string point;
    char trash=' ', minus=' ';
    num=0;
    denom=1;
    if(store.peek()=='-')
        store >> minus;
    store >> num;
    if(store.peek()=='/' || store.peek()=='.')
        store >> trash;
    if(trash=='/' && store.peek()!=-1)
        store >> denom;
    if(trash=='.' && store.peek()!=-1) {
        while(store.peek()>='0' && store.peek()<='9') {
            char decimal;
            store>>decimal;
            point+=decimal;
        }
        denom = (int)pow(10., point.length());
        stringstream temp(point);
        int dec;
        temp >> dec;
        num = num * denom + dec;
    }
    if(minus == '-')
        num *= -1;
    simplify();
}

// Protected
void Fraction::set(int n, int d) {
    num = n;
    denom = d;
    simplify();
}

// Public
Fraction::Fraction(string num) {
    convert(num);
}

Fraction::Fraction(double num) {
    stringstream temp;
    string toStr = "";
    temp << num;
    temp >> toStr;
    convert(toStr);
}

Fraction::Fraction(int num, int denom) {
    this->num = num;
    this->denom = denom;
    simplify();
}

Fraction::Fraction(const Fraction& copy) {
    num = copy.num;
    denom = copy.denom;
}

Fraction::~Fraction() { }

Fraction& Fraction::operator = (const Fraction& rhs) {
    if(this != &rhs) {
        num = rhs.num;
        denom = rhs.denom;
    }
    return *this;
}

const int& Fraction::getNum() const {
    return num;
}

const int& Fraction::getDenom() const {
    return denom;
}

void Fraction::display() const {
    cout << num;
    if(denom>1)
        cout << '/' << denom;
}

double Fraction::stod() const {
    return (double)num/denom;
}

int Fraction::stoi() const {
    return num/denom;
}

Fraction& Fraction::operator += (const Fraction& rhs) {
    *this = *this + rhs;
    return *this;
}

Fraction& Fraction::operator -= (const Fraction& rhs) {
    *this = *this - rhs;
    return *this;
}

Fraction& Fraction::operator *= (const Fraction& rhs) {
    *this = *this * rhs;
    return *this;
}

Fraction& Fraction::operator /= (const Fraction& rhs) {
    *this = *this / rhs;
    return *this;
}

// Friends
Fraction operator + (const Fraction& lhs, const Fraction& rhs) {
    int n = lhs.num*rhs.denom + rhs.num*lhs.denom,
        d = lhs.denom * rhs.denom;
    return Fraction(n,d);
}

Fraction operator - (const Fraction& lhs, const Fraction& rhs) {
    int n = lhs.num*rhs.denom - rhs.num*lhs.denom,
        d = lhs.denom * rhs.denom;
    return Fraction(n,d);
}

Fraction operator * (const Fraction& lhs, const Fraction& rhs) {
    int n = lhs.num * rhs.num,
        d = lhs.denom * rhs.denom;
    return Fraction(n,d);
}

Fraction operator / (const Fraction& lhs, const Fraction& rhs) {
    int n = lhs.num * rhs.denom,
        d = lhs.denom * rhs.num;
    return Fraction(n,d);
}

ostream& operator << (ostream& out, const Fraction& f) {
    out << f.num;
    if(f.denom!=1)
        out << "/" << f.denom;
    return out;
}

istream& operator >> (istream& in, Fraction& f) {
    char junk, oneByOne;
    f.denom = 1;
    if(in.peek()=='-')
        junk = '-';
    in >> f.num;
    if(in.peek() == '/') {
        in >> junk >> f.denom;
        f.simplify();
    } else if(in.peek()=='.') {
        stringstream store(to_string(f.num));
        string number = "", temp = "";
        if(junk == '-' && f.num == 0)
            store << junk;
        in >> junk;
        while(in.peek()>47 && in.peek()<58) {
            in >> oneByOne;
            temp += oneByOne;
        }
        store << f.num << junk << temp;
        store >> number;
        f = Fraction(number);
    }
    return in;
}

bool operator>(const Fraction& lhs, const Fraction& rhs) {
    return (double)lhs.num/lhs.denom > (double)rhs.num/rhs.denom;
}

bool operator<(const Fraction& lhs, const Fraction& rhs) {
    return (double)lhs.num/lhs.denom < (double)rhs.num/rhs.denom;
}

bool operator>=(const Fraction& lhs, const Fraction& rhs) {
    return (double)lhs.num/lhs.denom >= (double)rhs.num/rhs.denom;
}

bool operator<=(const Fraction& lhs, const Fraction& rhs) {
    return (double)lhs.num/lhs.denom <= (double)rhs.num/rhs.denom;
}

bool operator==(const Fraction& lhs, const Fraction& rhs) {
    return lhs.num==rhs.num && lhs.denom==rhs.denom;
}

bool operator!=(const Fraction& lhs, const Fraction& rhs) {
    return !(lhs.num==rhs.num && lhs.denom==rhs.denom);
}
