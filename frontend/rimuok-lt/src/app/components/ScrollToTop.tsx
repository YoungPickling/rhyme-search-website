"use client"
import Image from "next/image";
import { useEffect } from "react";

export default function ScrollToTop() {
  const scrolPos = 700;

  useEffect(() => {
    let toggle = false;

    const handleScroll = () => {
      const btn = document.getElementById("scrollBtn");

      if (btn) {
        if (window.scrollY > scrolPos && !toggle) {
          toggle = true;
          btn.style.animationTimingFunction = "ease-out";
          btn.style.animationName = "r_scroll_btn_up";
          btn.style.display = "block";
          // btn.removeEventListener("animationend", () => {
          //   btn.style.display = "block";
          // });
        } else if (window.scrollY <= scrolPos && toggle) {
          toggle = false;
          btn.style.animationTimingFunction = "ease-in";
          btn.style.animationName = "r_scroll_btn_down";
          // btn.style.display = "none";
          // btn.addEventListener("animationend", () => {
          //   btn.style.display = "none";
          // });
        }
      }
    };

    const handleAnimationEnd = () => {
      const btn = document.getElementById("scrollBtn");
      if (btn) {
        btn.style.display = window.scrollY > scrolPos ? "block" : "none";
      }
    };
  
    window.addEventListener("scroll", handleScroll);

    const handleClick = () => {
      window.scrollTo(0, 0);
      handleAnimationEnd;
    };

    const btn = document.getElementById("scrollBtn");
    if (btn) {
      btn.addEventListener("animationend", handleAnimationEnd);
      btn.addEventListener("click", handleClick);
    }
  
    // Cleanup the event listeners on component unmount
    return () => {
      window.removeEventListener("scroll", handleScroll);
      if (btn) {
        btn.removeEventListener("animationend", handleAnimationEnd);
        btn.removeEventListener("click", handleClick);
      }
    };
    
  }, []);

  const handleScrollUp = () => {
    window.scrollTo(0, 0);
  };


  return (
    <div className="r_scroll_to_top" id="scrollBtn">
      <Image 
        src="/up.svg"
        alt="Grižti į viršų"
        width="60"
        height="60"
      />
    </div>
  );
}