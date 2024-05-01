import {WebUntisAnonymousAuth, WebUntisElementType} from 'webuntis';
import fs from 'fs';

const untis = new WebUntisAnonymousAuth('Gym_MT', 'nessa.webuntis.com');
await untis.login();
let week = new Date();
week.setDate(week.getDate() + 7);
const timetable = await untis.getTimetableForRange(new Date(), week, 1042, WebUntisElementType.CLASS);
fs.writeFile('./configs/timetable.conf', JSON.stringify(timetable), (err) => {
    if (err) throw err;
})