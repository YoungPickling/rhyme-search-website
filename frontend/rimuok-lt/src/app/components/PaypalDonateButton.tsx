// "use client"
// import React, { useEffect } from 'react';

// const DonateButton = () : any => {
//   useEffect(() => {
//     const loadPayPalScript = () => {
//       const script = document.createElement('script');
//       script.src = 'https://www.paypalobjects.com/donate/sdk/donate-sdk.js';
//       script.async = true;
//       script.charset = 'UTF-8';
//       script.onload = () : any => {
//         (PayPal as any | undefined)?.Donation.Button({
//           env: 'production',
//           hosted_button_id: '9N4HTMTBTGTCU',
//           image: {
//             src: 'https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif',
//             alt: 'Donate with PayPal button',
//             title: 'PayPal - The safer, easier way to pay online!',
//           },
//         }).render('#donate-button');
//       };
//       document.body.appendChild(script);
//     };

//     loadPayPalScript();
//   }, []);

//   return (
//     <div id="donate-button-container">
//       <div id="donate-button"></div>
//     </div>
//   );
// };

// export default DonateButton;