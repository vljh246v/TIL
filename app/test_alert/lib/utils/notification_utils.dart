import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:timezone/data/latest.dart' as tz;
import 'package:timezone/timezone.dart' as tz;
import 'package:intl/intl.dart';


FlutterLocalNotificationsPlugin flutterLocalNotificationsPlugin =
FlutterLocalNotificationsPlugin();

Future<void> initializeNotifications() async {
  tz.initializeTimeZones();

  const AndroidInitializationSettings initializationSettingsAndroid =
  AndroidInitializationSettings('@mipmap/ic_launcher');

  final IOSInitializationSettings initializationSettingsIOS =
  IOSInitializationSettings(
    requestSoundPermission: false,
    requestBadgePermission: false,
    requestAlertPermission: false,
  );

  final InitializationSettings initializationSettings = InitializationSettings(
    android: initializationSettingsAndroid,
    iOS: initializationSettingsIOS,
  );

  await flutterLocalNotificationsPlugin.initialize(initializationSettings);
}
Future<void> scheduleNotification(
    int hours, int minutes, String timezone) async {
  final int notificationId = _getNotificationId(hours, minutes, timezone);
  final String timeZone = _getTimeZone(timezone);
  final tz.TZDateTime now = tz.TZDateTime.now(tz.getLocation(timeZone));
  final tz.TZDateTime scheduledDate = tz.TZDateTime(
      tz.getLocation(timeZone), now.year, now.month, now.day, hours, minutes)
      .add(Duration(hours: _getTimeZoneOffset(timezone)));


  final AndroidNotificationDetails androidPlatformChannelSpecifics =
  AndroidNotificationDetails(
    'test_alert_notification_channel',
    'Test Alert Notification Channel',
    'Test Alert Notification Channel',
    importance: Importance.max,
    priority: Priority.high,
  );

  final NotificationDetails platformChannelSpecifics =
  NotificationDetails(android: androidPlatformChannelSpecifics);

  await flutterLocalNotificationsPlugin.zonedSchedule(
    notificationId,
    'Test Alert',
    'It is time to wake up!',
    scheduledDate,
    platformChannelSpecifics,
    androidAllowWhileIdle: true,
    uiLocalNotificationDateInterpretation:
    UILocalNotificationDateInterpretation.absoluteTime,
    matchDateTimeComponents: DateTimeComponents.time,
    payload: notificationId.toString()
  );
}

tz.TZDateTime _nextInstanceOfTime(int hours, int minutes) {
  final tz.TZDateTime now = tz.TZDateTime.now(tz.local);
  tz.TZDateTime scheduledDate =
  tz.TZDateTime(tz.local, now.year, now.month, now.day, hours, minutes);
  if (scheduledDate.isBefore(now)) {
    scheduledDate = scheduledDate.add(const Duration(days: 1));
  }
  return scheduledDate;
}

void cancelNotification(int hours, int minutes, String timezone) {
  final int notificationId = _getNotificationId(hours, minutes, timezone);
  flutterLocalNotificationsPlugin.cancel(notificationId);
}

int _getTimeZoneOffset(String timezone) {
  final DateTime now = DateTime.now().toUtc();
  final String timeZone = _getTimeZone(timezone);
  final Duration offset = now.timeZoneOffset;
  final Duration timezoneOffset = DateTime.parse('${now.year}-01-01T00:00:00.000000Z').timeZoneOffset;

  int currentOffset = offset.inSeconds - timezoneOffset.inSeconds;
  int targetOffset = tz.TZDateTime.now(tz.getLocation(timeZone)).timeZoneOffset.inSeconds;

  return targetOffset - currentOffset;
}

int _getNotificationId(int hours, int minutes, String timezone) {
  final String timeZone = _getTimeZone(timezone);
  final tz.TZDateTime now = tz.TZDateTime.now(tz.getLocation(timeZone));
  final tz.TZDateTime scheduledDate =
  tz.TZDateTime(tz.getLocation(timeZone), now.year, now.month, now.day, hours, minutes);

  return scheduledDate.millisecondsSinceEpoch ~/ 1000;
}

String _getTimeZone(String timezone) {
  switch (timezone) {
    case 'Asia/Seoul':
      return 'Asia/Seoul';
    case 'Europe/London':
      return 'Europe/London';
    case 'America/New_York':
      return 'America/New_York';
    default:
      throw ArgumentError('Invalid timezone: $timezone');
  }
}