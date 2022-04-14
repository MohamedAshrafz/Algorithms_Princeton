#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <queue>
using namespace std;

class process {

private:
	int id;
	double burst;
	double arrival;
	int priority;

public:
	process(int id, double arrival, double burst, int priority = 1)
		:id{ id }, arrival{ arrival }, burst{ burst }, priority{ priority } {}

	double getBurst() const {
		return this->burst;
	}

	double getArrival() const {
		return this->arrival;
	}

	int getID() const {
		return this->id;
	}

	void setBurst(double burst) {
		this->burst = burst;
	}
};

class TimeSlot {
private:
	int id;
	double start;
	double end;

public:
	TimeSlot(int id, double start, double end)
		: id{ id }, start{ start }, end{ end } {}

	double getId() const {
		return this->id;
	}

	double getStart() const {
		return this->start;
	}

	double getEnd() const {
		return this->end;
	}

};

ostream& operator<<(ostream& os, TimeSlot& ts) {
	os << ts.getId() << "\t" << ts.getStart() << "\t" << ts.getEnd();
	return os;
}

struct compareBurst {
	bool operator() (process const& a, process const& b) const {
		return a.getBurst() > b.getBurst();
	}
} myobj1;

struct compareArrival {
	bool operator() (process const& a, process const& b) const {
		return a.getArrival() < b.getArrival();
	}
} myobj2;

int main() {

	vector<process> processes;

	processes.push_back(process(1, 0, 1));
	processes.push_back(process(2, 2, 2));
	processes.push_back(process(3, 3, 3));
	processes.push_back(process(4, 5, 3));


	sort(processes.begin(), processes.end(), myobj2);
	priority_queue<process, vector<process>, compareBurst> pqProcesses;
	vector<TimeSlot> vecTS;

	size_t i = 0;
	while (i < processes.size() || !pqProcesses.empty()) {

		double currentArrival;
		if (vecTS.size() == 0)
			currentArrival = processes.at(i).getArrival();
		else
			currentArrival = vecTS.at(vecTS.size() - 1).getEnd();


		for (; i < processes.size(); i++) {
			if (currentArrival >= processes.at(i).getArrival())
				pqProcesses.push(processes.at(i));
			else
				break;
		}

		double currentBurst;
		if (i < processes.size())
			currentBurst = processes.at(i).getArrival() - currentArrival;
		else
			currentBurst = DBL_MAX;

		if (!pqProcesses.empty()) {
			process p = pqProcesses.top();
			pqProcesses.pop();
			if (p.getBurst() - currentBurst > 0) {
				TimeSlot ts1(p.getID(), currentArrival, currentArrival + currentBurst);
				p.setBurst(p.getBurst() - currentBurst);
				pqProcesses.push(p);
				vecTS.push_back(ts1);
			}
			else {
				TimeSlot ts1(p.getID(), currentArrival, currentArrival + p.getBurst());
				vecTS.push_back(ts1);
			}
		}
		else {
			vecTS.push_back(TimeSlot(-1, currentArrival, currentArrival + currentBurst));
		}
	}

	for(TimeSlot ts : vecTS)
	{
		cout << ts << endl;
	}
	return 0;
}