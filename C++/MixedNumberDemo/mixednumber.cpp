#include "MixedNumber.h"

MixedNumber::MixedNumber() { }

MixedNumber::MixedNumber(int w, int num, int denom) {
    set(denom*w+num, denom);
}

MixedNumber::~MixedNumber() { }

MixedNumber::MixedNumber(const MixedNumber &other) {
    copy(other);
}

MixedNumber::MixedNumber(const Fraction& other) {
    copy(other);
}

MixedNumber& MixedNumber::operator=(const MixedNumber &other) {
    if(this != &other)
        copy(other);
    return *this;
}

void MixedNumber::copy(const MixedNumber& other) {
    set(other.getNum(), other.getDenom());
}

void MixedNumber::copy(const Fraction& other) {
    Fraction::operator=(other);
}

ostream& operator<<(ostream &out, const MixedNumber &m) {
    int whole = m.getNum() / m.getDenom(),
        num = m.getNum() % m.getDenom();
    if(whole != 0 || num == 0)
        out<<whole<<" ";
    if(num != 0)
        out<<num<<"/"<<m.getDenom();
    return out;
}

istream& operator>>(istream &in, MixedNumber &m) {
    int whole;
    Fraction temp;
    in >> whole;
    if(in.peek()==' ') {
        in >> temp;
        if(temp.getNum()!=0)
            m = MixedNumber(whole, temp.getNum(), temp.getDenom());
        else {
            m = MixedNumber(0,whole,1);
            in.clear();
            in.seekg(ios::beg);
            in >> whole;
        }
    } else {
        in.clear();
        string data(to_string(whole));
        for(size_t i=0; i<data.length(); ++i)
            in.putback(data.at(i));
        in >> temp;
        m = temp;
    }
    return in;
}
