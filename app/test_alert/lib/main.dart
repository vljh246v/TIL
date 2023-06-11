import 'package:flutter/material.dart';
import 'package:test_alert/utils/notification_utils.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await initializeNotifications(); // 알림 초기화
  runApp(MaterialApp(
    home: AlarmScreen(),
  ));
}

class AlarmScreen extends StatefulWidget {
  const AlarmScreen({Key? key}) : super(key: key);

  @override
  _AlarmScreenState createState() => _AlarmScreenState();
}

class _AlarmScreenState extends State<AlarmScreen> {
  String _selectedTimezone = 'Asia/Seoul';

  final TextEditingController _hoursController = TextEditingController();
  final TextEditingController _minutesController = TextEditingController();
  final TextEditingController _timezoneController = TextEditingController();
  final List<_Alarm> _alarms = [];

  @override
  void initState() {
    super.initState();
    _getAlarms();
  }

  Future<void> _getAlarms() async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    final List<String> alarmStrings = prefs.getStringList('alarms') ?? [];

    setState(() {
      _alarms.clear();
      _alarms.addAll(alarmStrings.map((String alarmString) {
        final List<String> parts = alarmString.split(':');
        return _Alarm(int.parse(parts[0]), int.parse(parts[1]), parts[2]);
      }));
    });
  }

  Future<void> _saveAlarms() async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    final List<String> alarmStrings = _alarms.map((alarm) {
      return '${alarm.hours}:${alarm.minutes}:${alarm.timezone}';
    }).toList();

    await prefs.setStringList('alarms', alarmStrings);
  }

  void _addAlarm() {
    setState(() {
      final int hours = int.tryParse(_hoursController.text) ?? 0;
      final int minutes = int.tryParse(_minutesController.text) ?? 0;
      final String timezone = _timezoneController.text.trim();

      _alarms.add(_Alarm(hours, minutes, timezone));
      _hoursController.clear();
      _minutesController.clear();
      _timezoneController.clear();

      scheduleNotification(hours, minutes, timezone);
      _saveAlarms();
    });
  }

  void _deleteAlarm(int index) {
    setState(() {
      final _Alarm alarm = _alarms[index];
      _alarms.removeAt(index);
      cancelNotification(alarm.hours, alarm.minutes, alarm.timezone);
      _saveAlarms();
    });
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: const Text('Alarm'),
      ),
      body: Column(
        children: [
          Row(
            children: [
              Expanded(
                child: TextField(
                  controller: _hoursController,
                  decoration: const InputDecoration(
                    labelText: 'Hours',
                  ),
                  keyboardType: TextInputType.number,
                ),
              ),
              Expanded(
                child: TextField(
                  controller: _minutesController,
                  decoration: const InputDecoration(
                    labelText: 'Minutes',
                  ),
                  keyboardType: TextInputType.number,
                ),
              ),
              Expanded(
                child: DropdownButton<String>(
                  value: _selectedTimezone,
                  onChanged: (String? newValue) {
                    if (newValue != null) {
                      setState(() {
                        _selectedTimezone = newValue;
                      });
                    }
                  },
                  items: <String>[
                    'Asia/Seoul',
                    'Europe/London',
                    'America/New_York',
                  ].map<DropdownMenuItem<String>>((String value) {
                    return DropdownMenuItem<String>(
                      value: value,
                      child: Text(value),
                    );
                  }).toList(),
                ),
              ),
              IconButton(
                onPressed: _addAlarm,
                icon: const Icon(Icons.add),
              ),
            ],
          ),
          Expanded(
            child: ListView.builder(
              itemCount: _alarms.length,
              itemBuilder: (BuildContext context, int index) {
                final _Alarm alarm = _alarms[index];
                return ListTile(
                  title: Text('${alarm.hours}:${alarm.minutes} ${alarm.timezone}'),
                  trailing: IconButton(
                    onPressed: () {
                      _deleteAlarm(index);
                    },
                    icon: const Icon(Icons.delete),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}

class _Alarm {
  const _Alarm(this.hours, this.minutes, this.timezone);

  final int hours;
  final int minutes;
  final String timezone;
}