import 'package:flutter/material.dart';
import 'package:random_number_generator/component/number_row.dart';
import 'package:random_number_generator/constant/color.dart';

class SettingsScreen extends StatefulWidget {
  final int maxNumber;

  const SettingsScreen({
    required this.maxNumber,
    Key? key,
  }) : super(key: key);

  @override
  State<SettingsScreen> createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  double maxNumber = 1000;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();

    maxNumber = widget.maxNumber.toDouble();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: PRIMARY_COLOR,
        body: SafeArea(
          child: Padding(
            padding: const EdgeInsets.symmetric(
              horizontal: 16.0,
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                _Body(maxNumber: maxNumber),
                _Fotter(
                  maxNumber: maxNumber,
                  onSliderChanged: onSliderChanged,
                  onButtonPressed: onButtonPressed,
                ),
              ],
            ),
          ),
        ));
  }

  void onSliderChanged(double value) {
    setState(() {
      maxNumber = value;
    });
  }

  void onButtonPressed() {
    Navigator.of(context).pop(maxNumber.toInt());
  }
}

class _Body extends StatelessWidget {
  final double maxNumber;

  const _Body({
    required this.maxNumber,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: NumberRow(
        number: maxNumber.toInt(),
      )
    );
  }
}

class _Fotter extends StatelessWidget {
  final double maxNumber;
  final ValueChanged<double>? onSliderChanged;
  final VoidCallback onButtonPressed;

  const _Fotter({
    required this.onButtonPressed,
    required this.onSliderChanged,
    required this.maxNumber,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        Slider(
            value: maxNumber,
            min: 1000,
            max: 100000,
            onChanged: onSliderChanged),
        ElevatedButton(
          onPressed: onButtonPressed,
          child: Text('저장!'),
          style: ElevatedButton.styleFrom(
            backgroundColor: RED_COLOR,
          ),
        )
      ],
    );
  }
}
