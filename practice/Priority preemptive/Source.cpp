#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <queue>
using namespace std;

class process {
public:
	int id;
	double burst;
	double arrival;
	int priority;

	process(int id, double arrival, double burst, int priority = 1)
		:id{ id }, arrival{ arrival }, burst{ burst }, priority{ priority } {}

};

class TimeSlot {
public:
	int id;
	double start;
	double end;

	TimeSlot(int id, double start, double end)
		: id{ id }, start{ start }, end{ end } {}
};

ostream& operator<<(ostream& os, TimeSlot& ts) {
	os << ts.id << "\t\t" << ts.start << "\t" << ts.end;
	return os;
}

struct compareArrival {
	bool operator() (process const& a, process const& b) const {
		return a.arrival < b.arrival;
	}
} myobj2;

struct comparePriority {
	bool operator() (process const& a, process const& b) const {
		return a.priority > b.priority;
	}
} myobj3;

vector <TimeSlot> PriorityPreemptive(vector<process> processes);

int main() {

	vector<process> processes;

	processes.push_back(process(1, 0.5, 10, 3));
	processes.push_back(process(2, 1, 1, 1));
	processes.push_back(process(3, 2, 2, 3));
	processes.push_back(process(4, 0, 1, 4));
	processes.push_back(process(5, 3, 5, 2));

	vector <TimeSlot> vecTS = PriorityPreemptive(processes);

	cout << "process ID" << "\t" << "start" << "\t" << "end" << endl;
	for (TimeSlot ts : vecTS)
	{
		cout << ts << endl;
	}
	return 0;
}

vector <TimeSlot> PriorityPreemptive(vector <process> processes) {
	sort(processes.begin(), processes.end(), myobj2);
	priority_queue<process, vector<process>, comparePriority> pqProcesses;
	vector<TimeSlot> vecTS;

	size_t i = 0;
	while (i < processes.size() || !pqProcesses.empty()) {

		double currentArrival{};
		if (vecTS.size() == 0)
			currentArrival = processes.at(i).arrival;
		else
			currentArrival = vecTS.at(vecTS.size() - 1).end;


		for (; i < processes.size(); i++) {
			if (currentArrival >= processes.at(i).arrival)
				pqProcesses.push(processes.at(i));
			else
				break;
		}

		double currentBurst;
		if (i < processes.size())
			currentBurst = processes.at(i).arrival - currentArrival;
		else
			currentBurst = DBL_MAX;

		if (!pqProcesses.empty()) {
			process p = pqProcesses.top();
			pqProcesses.pop();
			if (p.burst - currentBurst > 0 && vecTS.size() > 0
				&& vecTS.at(vecTS.size() - 1).id == p.id) {

				vecTS.at(vecTS.size() - 1).end = currentArrival + currentBurst;
				p.burst = (p.burst - currentBurst);
				pqProcesses.push(p);
			}
			else if (p.burst - currentBurst > 0) {
				TimeSlot ts1(p.id, currentArrival, currentArrival + currentBurst);
				vecTS.push_back(ts1);
				p.burst = (p.burst - currentBurst);
				pqProcesses.push(p);
			}
			else if (vecTS.size() > 0
				&& vecTS.at(vecTS.size() - 1).id == p.id) {
				vecTS.at(vecTS.size() - 1).end = currentArrival + p.burst;
			}
			else {
				TimeSlot ts1(p.id, currentArrival, currentArrival + p.burst);
				vecTS.push_back(ts1);
			}
		}
		else {
			vecTS.push_back(TimeSlot(-1, currentArrival, currentArrival + currentBurst));
		}
	}
	return vecTS;
}