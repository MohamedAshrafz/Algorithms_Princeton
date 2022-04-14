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

	double getEnd() const {
		return this->end;
	}

};

struct comapreBurst {
	bool operator() (process const& a, process const& b) const {
		return a.getBurst() < b.getBurst();
	}
};

struct comapreArrival {
	bool operator() (process const& a, process const& b) const {
		return a.getArrival() < b.getArrival();
	}
} myobj;

int main() {

	vector<process> processes;
	sort(processes.begin(), processes.end(), myobj);
	priority_queue<process, vector<process>, comapreBurst> pqProcesses;
	vector<TimeSlot> vecTS;

	size_t i = 0;
	while (i < processes.size() || !pqProcesses.empty()) {

		double currentArrival;
		if (vecTS.size() == 0) {
			currentArrival = processes.at(i).getArrival();
			pqProcesses.push(processes.at(i));
		}
		else {
			currentArrival = vecTS.at(vecTS.size() - 1).getEnd();
		}
		

		for (i = i + 1; i < processes.size(); i++) {
			if (currentArrival <= processes.at(i).getArrival())
				pqProcesses.push(processes.at(i));
			else
				break;
		}

		double currentBurst = currentArrival - processes.at(i).getArrival();

		process p = pqProcesses.top();
		pqProcesses.pop();
		if (p.getBurst() - currentBurst > 0) {
			TimeSlot ts1(p.getID(), currentArrival, currentArrival + currentBurst);
			p.setBurst(p.getBurst() - currentBurst);
			vecTS.push_back(ts1);
		}
		else {
			TimeSlot ts1(p.getID(), currentArrival, currentArrival + p.getBurst());
			p.setBurst(0.0);
			vecTS.push_back(ts1);
		}
	}

	return 0;
}